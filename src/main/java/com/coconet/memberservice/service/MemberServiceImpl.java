package com.coconet.memberservice.service;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.dto.*;
import com.coconet.memberservice.dto.client.MemberClientDto;
import com.coconet.memberservice.dto.client.MemberRoleResponse;
import com.coconet.memberservice.dto.client.MemberStackResponse;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.*;
import com.coconet.memberservice.security.oauthModel.AuthProvider;
import com.coconet.memberservice.security.token.TokenProvider;
import com.coconet.memberservice.security.token.converter.TokenConverter;
import com.coconet.memberservice.security.token.dto.TokenDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberStackRepository memberStackRepository;
    private final TokenProvider tokenProvider;
    private final TokenConverter tokenConverter;
    private final MemberRegisterService memberRegisterService;
    private final String UNDEFINED = "Undefined";

    public TokenResponse login (String email) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"));

        if(member.getName().equals(UNDEFINED))
            return TokenResponse.builder()
                    .memberUUID(member.getMemberUUID())
                    .build();

        MemberTokenDto memberTokenDto = MemberTokenDto.builder()
                .memberUUID(member.getMemberUUID())
                .email(member.getEmail())
                .build();
        TokenDto accessToken = tokenProvider.issueAccessToken(memberTokenDto);
        TokenDto refreshToken = tokenProvider.issueRefreshToken(memberTokenDto);
        TokenResponse tokenResponse = tokenConverter.toResponse(accessToken, refreshToken);
        return tokenResponse;
    }

    public UUID preRegister(AuthProvider provider, String email) {
        MemberEntity user = MemberEntity.builder()
                .email(email)
                .name(UNDEFINED)
                .career(UNDEFINED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .provider(provider)
                .isActivated((byte) 1)
                .memberUUID(UUID.randomUUID())
                .build();

        memberRepository.save(user);

        return user.getMemberUUID();
    }

    public TokenResponse register(MemberRegisterRequestDto requestDto, MultipartFile imageFile) {
        MemberEntity preRegisterMember = memberRepository.findByMemberUUID(requestDto.getMemberUUID())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"));

        if(checkMemberNickName(requestDto.getName())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "The name is already used");
        }

        preRegisterMember.register(requestDto.getName(),
                        String.valueOf(requestDto.getCareer()),
                        updateProfilePic(preRegisterMember, imageFile),
                        requestDto.getGithubLink(),
                        requestDto.getBlogLink(),
                        requestDto.getNotionLink(),
                        requestDto.getBio());

        memberRegisterService.addRoles(preRegisterMember, requestDto.getRoles());
        memberRegisterService.addStacks(preRegisterMember, requestDto.getStacks());

        MemberEntity returnedMember = memberRepository.save(preRegisterMember);

        return login(returnedMember.getEmail());
    }

    public String updateProfilePic(MemberEntity member, MultipartFile image) {
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "src/main/resources/memberProfilePics";

        if (!image.isEmpty()) {
            String imagePath = path + "/" + member.getId() + ".png";
            File file = new File(absolutePath + imagePath);

            try {
                image.transferTo(file.toPath());
            } catch (IOException e) {
                throw new ApiException(ErrorCode.SERVER_ERROR, "Error happened when file is created");
            }
            return imagePath;

        } else {
            File previousFile = new File(absolutePath + path + "/" + member.getId() + ".png");
            if(previousFile.exists())
                previousFile.delete();
            return path + "/basic_image.png";
        }
    }

    public MemberResponseDto getUserInfo(UUID memberId){
        return memberRepository.getUserInfo(memberId);
    }

    public MemberResponseDto updateUserInfo(UUID memberId, @Valid MemberRequestDto requestDto, MultipartFile imageFile) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                                                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        if( !member.getName().equals(requestDto.getName()) && checkMemberNickName(requestDto.getName())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "The name is already used");
        }

        member.register(requestDto.getName(),
                String.valueOf(requestDto.getCareer()),
                updateProfilePic(member, imageFile),
                requestDto.getGithubLink(),
                requestDto.getBlogLink(),
                requestDto.getNotionLink(),
                requestDto.getBio());

        memberRegisterService.updateRoles(member, requestDto.getRoles());
        memberRegisterService.updateStacks(member, requestDto.getStacks());

        return memberRepository.getUserInfo(memberId);
    }

    public MemberResponseDto deleteUser(UUID memberId) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        member.deleteUser();

        List<MemberRoleEntity> rolesToRemove = memberRoleRepository.getAllRoles(member);
        List<MemberStackEntity> stacksToRemove = memberStackRepository.getAllStacks(member);

        memberRoleRepository.deleteAllInBatch(rolesToRemove);
        memberStackRepository.deleteAllInBatch(stacksToRemove);

        return MemberResponseDto.toEntity(member);
    }

    public MemberClientDto getMemberId(UUID memberUUID) {
        MemberEntity member = memberRepository.findByMemberUUID(memberUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"));

        List<MemberRoleResponse> roles = member.getMemberRoles().stream()
                .map(v -> MemberRoleResponse.builder()
                        .name(v.getRole().getName())
                        .build())
                .toList();
        List<MemberStackResponse> stacks = member.getMemberStacks().stream()
                .map(v -> MemberStackResponse.builder()
                        .name(v.getTechStack().getName())
                        .category(v.getTechStack().getCategory())
                        .image(v.getTechStack().getImage())
                        .build())
                .toList();

        return new MemberClientDto(member.getEmail(), member.getName(), member.getMemberUUID(),
                member.getProfileImage(), roles, stacks, member.getCreatedAt(), member.getUpdatedAt()
        );
    }

    public Boolean checkMemberNickName(String nickName) {
        return memberRepository.findByName(nickName).isPresent();
    }
}

