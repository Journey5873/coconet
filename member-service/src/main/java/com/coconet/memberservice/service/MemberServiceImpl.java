package com.coconet.memberservice.service;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.errorcode.ErrorCodeIfs;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.MemberIdDto;
import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.*;
import com.coconet.memberservice.security.auth.MemberPrincipal;
import com.coconet.memberservice.security.oauth.model.AuthProvider;
import com.coconet.memberservice.security.token.TokenProvider;
import com.coconet.memberservice.security.token.converter.TokenConverter;
import com.coconet.memberservice.security.token.dto.TokenDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final RoleRepository roleRepository;
    private final TechStackRepository techStackRepository;
    private final MemberStackRepository memberStackRepository;
    private final TokenProvider tokenProvider;
    private final TokenConverter tokenConverter;

    public boolean existMember(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
    public UUID preRegister(AuthProvider provider, String email) {
        MemberEntity user = MemberEntity.builder()
                .email(email)
                .name("Undefined")
                .career("Undefined")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .provider(provider)
                .isActivated((byte) 1)
                .memberUUID(UUID.randomUUID())
                .build();

        memberRepository.save(user);

        return user.getMemberUUID();
    }

    public TokenResponse login (String email) {
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"));

        if(member.getName().equals("Undefined")) {
            return TokenResponse.builder()
                    .memberId(member.getMemberUUID()).build();
        }
        else {
            MemberPrincipal memberPrincipal = new MemberPrincipal(member);
            TokenDto accessToken = tokenProvider.issueAccessToken(memberPrincipal);
            TokenDto refreshToken = tokenProvider.issueRefreshToken(memberPrincipal);
            TokenResponse tokenResponse = tokenConverter.toResponse(accessToken, refreshToken);
            return tokenResponse;
        }
    }

    public TokenResponse register(MemberRegisterRequestDto requestDto) {
        MemberEntity preRegisterMember = memberRepository.findByMemberUUID(requestDto.getMemberId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"));

        if(memberRepository.findByName(requestDto.getName()).stream().findAny().isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "The name is already used");
        }

        preRegisterMember.register(requestDto.getName(),
                        String.valueOf(requestDto.getCareer()),
                        requestDto.getGithubLink(),
                        requestDto.getBlogLink(),
                        requestDto.getNotionLink(),
                requestDto.getBio());

        List<String> returnRoles = addRoles(preRegisterMember, requestDto.getRoles());
        List<String> returnStacks = addStacks(preRegisterMember, requestDto.getStacks());
        MemberEntity returnedMember = memberRepository.save(preRegisterMember);

        return login(returnedMember.getEmail());
    }

    public MemberResponseDto getUserInfo(UUID memberId){
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        List<String> returnRoles = getAllRoles(member).stream()
                .map(RoleEntity::getName)
                .toList();
        List<String> returnStacks = getAllStacks(member).stream()
                .map(TechStackEntity::getName)
                .toList();

        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(member.getProfileImage())
                .bio(member.getBio())
                .roles(returnRoles)
                .stacks(returnStacks)
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
    }

    public MemberResponseDto updateUserInfo(UUID memberId, MemberRequestDto requestDto, MultipartFile imageFile) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                                                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        if(memberRepository.findByName(requestDto.getName()).stream().findAny().isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "The name is already used");
        }

        member.changeName(requestDto.getName());
        member.changeCareer(String.valueOf(requestDto.getCareer()));
        member.changeProfileImage(updateProfilePic(member, imageFile));
        member.changeGithubLink(requestDto.getGithubLink());
        member.changeBlogLink(requestDto.getBlogLink());
        member.changeNotionLink(requestDto.getNotionLink());
        member.changeBio(requestDto.getBio());

        MemberEntity returnMember = memberRepository.save(member);

        List<String> returnRoles = updateRoles(member, requestDto.getRoles());
        List<String> returnStacks = updateStacks(member, requestDto.getStacks());

        return MemberResponseDto.builder()
                .name(returnMember.getName())
                .career(Integer.parseInt(returnMember.getCareer()))
                .profileImg(returnMember.getProfileImage())
                .roles(returnRoles)
                .stacks(returnStacks)
                .bio(returnMember.getBio())
                .githubLink(returnMember.getGithubLink())
                .blogLink(returnMember.getBlogLink())
                .notionLink(returnMember.getNotionLink())
                .build();
    }

    public MemberResponseDto deleteUser(UUID memberId) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        member.deleteUser();

        MemberEntity returnMember = memberRepository.save(member);

        List<String> returnRoles = getAllRoles(member).stream()
                .map(RoleEntity::getName)
                .toList();
        List<String> returnStacks = getAllStacks(member).stream()
                .map(TechStackEntity::getName)
                .toList();

        return MemberResponseDto.builder()
                .name(returnMember.getName())
                .career(Integer.parseInt(returnMember.getCareer()))
                .profileImg(returnMember.getProfileImage())
                .roles(returnRoles)
                .bio(returnMember.getBio())
                .stacks(returnStacks)
                .githubLink(returnMember.getGithubLink())
                .blogLink(returnMember.getBlogLink())
                .notionLink(returnMember.getNotionLink())
                .build();
    }

    public List<RoleEntity> getAllRoles(MemberEntity member) {
        List<MemberRoleEntity> roleEntities = memberRoleRepository.findAllByMemberId(member.getId());

        if(roleEntities.size() == 0) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Member must at least have one role");
        }

        return roleEntities.stream()
                .map(MemberRoleEntity::getRole)
                .toList();
    }

    public List<TechStackEntity> getAllStacks(MemberEntity member){
        List<MemberStackEntity> stackEntities = memberStackRepository.findByMemberId(member.getId());

        if(stackEntities.size() == 0) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Member must at least have one stack");
        }

        return stackEntities.stream()
                .map(MemberStackEntity::getTechStack)
                .toList();
    }

    public String updateProfilePic(MemberEntity member, MultipartFile image) {

        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "/";
        String path = "member-service/src/main/resources/memberProfilePics";

        if (!image.isEmpty()) {
            imagePath = path + "/" + member.getId() + ".png";
            File file = new File(absolutePath + imagePath);

            try {
                image.transferTo(file);
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

    public List<String> updateRoles(MemberEntity member, List<String> roles) {

        List<RoleEntity> inputRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No role found"))
                )
                .toList();

        // Get current roles
        List<RoleEntity> currentRoles = getAllRoles(member);

        // Identify new roles to add
        List<RoleEntity> rolesToAdd = inputRoles.stream()
                .filter(role -> !currentRoles.contains(role))
                .toList();

        // Identify roles to remove
        List<RoleEntity> rolesToRemove = currentRoles.stream()
                .filter(role -> !inputRoles.contains(role))
                .toList();

        // Create MemberRoleEntity to add
        List<MemberRoleEntity> memberRoleEntities = rolesToAdd.stream()
                .map(role -> new MemberRoleEntity(member, role))
                .toList();

        // save
        memberRoleRepository.saveAll(memberRoleEntities);

        // remove
        memberRoleRepository.deleteByMemberIdAndRoleIdIn(member.getId(), rolesToRemove.stream().map(role -> role.getId()).toList());

        return memberRoleRepository.findByMemberId(member.getId()).stream()
                .map(memberRoleEntity -> memberRoleEntity.getRole().getName())
                .toList();
    }

    List<String> addRoles(MemberEntity member, List<String> roles) {
        List<RoleEntity> inputRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No role found"))
                )
                .toList();

        List<MemberRoleEntity> memberRoleEntities = inputRoles.stream()
                .map(role -> new MemberRoleEntity(member, role))
                .toList();

        List<MemberRoleEntity> returnEntities = memberRoleRepository.saveAll(memberRoleEntities);
        return returnEntities.stream()
                .map(entity -> entity.getRole().getName())
                .toList();
    }
    List<String> addStacks(MemberEntity member, List<String> stacks) {
        List<TechStackEntity> inputStacks = stacks.stream()
                .map(stackName -> techStackRepository.findByName(stackName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No stack found"))
                )
                .toList();

        List<MemberStackEntity> memberStackMembers = inputStacks.stream()
                .map(stack -> new MemberStackEntity(member, stack))
                .toList();

        List<MemberStackEntity> returnEntites = memberStackRepository.saveAll(memberStackMembers);
        return returnEntites.stream()
                .map(entity -> entity.getTechStack().getName())
                .toList();
    }

    List<String> updateStacks(MemberEntity member, List<String> stacks){
        List<TechStackEntity> inputStacks = stacks.stream()
                .map(stackName -> techStackRepository.findByName(stackName)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No stack found"))
                )
                .toList();

        // Get current stacks
        List<TechStackEntity> currentStacks = getAllStacks(member);

        // Identify new stacks to add
        List<TechStackEntity> stacksToAdd = inputStacks.stream()
                .filter(stack -> !currentStacks.contains(stack))
                .toList();

        // Identify stacks to remove
        List<TechStackEntity> stacksToRemove = currentStacks.stream()
                .filter(stack -> !inputStacks.contains(stack))
                .toList();

        // Create MemberStackEntity to add
        List<MemberStackEntity> memberStackEntities = stacksToAdd.stream()
                .map(stack -> new MemberStackEntity(member, stack))
                .toList();

        // Save new stacks
        memberStackRepository.saveAll(memberStackEntities);

        // Delete stacks
        memberStackRepository.deleteByMemberIdAndTechStackIdIn(member.getId(), stacksToRemove.stream().map(stack -> stack.getId()).toList());

        return memberStackRepository.findByMemberId(member.getId()).stream()
                .map(memberStackEntity -> memberStackEntity.getTechStack().getName())
                .toList();
    }

    public String getJsonValue(String jsonStr, String key) {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonStr);
            return jsonObject.get(key).toString();
        }
        catch (ParseException e) {
            return "";
        }
    }

    public String decodeJwt(String jwt) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] chunks = jwt.split("\\.");
        return new String(decoder.decode(chunks[1]));
    }

    public MemberIdDto getMemberId(UUID memberUUID) {
        Long memberId = memberRepository.findByMemberUUID(memberUUID).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "No member found"))
                .getId();
        return new MemberIdDto(memberId);
    }
}

