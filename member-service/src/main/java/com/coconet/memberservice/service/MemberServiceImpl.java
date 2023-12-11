package com.coconet.memberservice.service;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.errorcode.ErrorCodeIfs;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.common.response.Response;
import com.coconet.memberservice.dto.*;
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

        addRoles(preRegisterMember, requestDto.getRoles());
        addStacks(preRegisterMember, requestDto.getStacks());

        MemberEntity returnedMember = memberRepository.save(preRegisterMember);

        return login(returnedMember.getEmail());
    }

    List<String> addRoles(MemberEntity member, List<String> roles) {

        if (roles == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to choose at least one role.");
        }

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

        if (stacks == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to choose at least one stack.");
        }

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

    public MemberResponseDto getUserInfo(UUID memberId){
        MemberInfoDto userInfo = memberRepository.getUserInfo(memberId);
        return DtoToResponseDto(userInfo);
    }

    public MemberResponseDto updateUserInfo(UUID memberId, MemberRequestDto requestDto, MultipartFile imageFile) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                                                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        if( !member.getName().equals(requestDto.getName()) && memberRepository.findByName(requestDto.getName()).stream().findAny().isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "The name is already used");
        }

        member.changeName(requestDto.getName());
        member.changeCareer(String.valueOf(requestDto.getCareer()));
        member.changeProfileImage(updateProfilePic(member, imageFile));
        member.changeGithubLink(requestDto.getGithubLink());
        member.changeBlogLink(requestDto.getBlogLink());
        member.changeNotionLink(requestDto.getNotionLink());
        member.changeBio(requestDto.getBio());

        updateRoles(member, requestDto.getRoles());
        updateStacks(member, requestDto.getStacks());

        MemberInfoDto userInfo = memberRepository.getUserInfo(memberId);

        return DtoToResponseDto(userInfo);
    }

    public void updateRoles(MemberEntity member, List<String> requestedRoleNames) {

        // Get current roles
        List<MemberRoleEntity> currentRoles = memberRoleRepository.getAllRoles(member);

        List<RoleEntity> requestedRoles = roleRepository.findByNameIn(requestedRoleNames);

        List<String> currentRoleNames = currentRoles.stream()
                .map(role -> role.getRole().getName())
                .toList();

        // Identify new roles to add
        List<RoleEntity> rolesToAdd = requestedRoles.stream()
                .filter(role -> !currentRoleNames.contains(role.getName()))
                .toList();

        // Identify roles to remove
        List<MemberRoleEntity> rolesToRemove = currentRoles.stream()
                .filter(role -> !requestedRoleNames.contains(role.getRole().getName()))
                .toList();

        // Create MemberRoleEntity to add
        List<MemberRoleEntity> memberRoleEntities = rolesToAdd.stream()
                .map(role -> new MemberRoleEntity(member, role))
                .toList();

        // save
        memberRoleRepository.saveAll(memberRoleEntities);
        // remove
        memberRoleRepository.deleteAllInBatch(rolesToRemove);
    }

    void updateStacks(MemberEntity member, List<String> requestedStackNames){

        // Get current stacks
        List<MemberStackEntity> currentStacks = memberStackRepository.getAllStacks(member);

        // Requested Stacks
        List<TechStackEntity> requestedStacks = techStackRepository.findByNameIn(requestedStackNames);

        List<String> currentStackNames = currentStacks.stream()
                .map(stack -> stack.getTechStack().getName())
                .toList();

        // Identify new stacks to add
        List<TechStackEntity> stacksToAdd = requestedStacks.stream()
                .filter(stack -> !currentStackNames.contains(stack.getName()))
                .toList();

        // Identify stacks to remove
        List<MemberStackEntity> stacksToRemove = currentStacks.stream()
                .filter(stack -> !requestedStackNames.contains(stack.getTechStack().getName()))
                .toList();

        // Create MemberStackEntity to add
        List<MemberStackEntity> memberStackEntities = stacksToAdd.stream()
                .map(stack -> new MemberStackEntity(member, stack))
                .toList();

        // Save new stacks
        memberStackRepository.saveAll(memberStackEntities);
        // Delete stacks
        memberStackRepository.deleteAllInBatch(stacksToRemove);
    }

    public MemberResponseDto deleteUser(UUID memberId) {
        MemberEntity member = memberRepository.findByMemberUUID(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No member found"));

        member.deleteUser();

        List<MemberRoleEntity> rolesToRemove = memberRoleRepository.getAllRoles(member);
        List<MemberStackEntity> stacksToRemove = memberStackRepository.getAllStacks(member);

        memberRoleRepository.deleteAllInBatch(rolesToRemove);
        memberStackRepository.deleteAllInBatch(stacksToRemove);

        return MemberResponseDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(member.getProfileImage())
                .roles(
                        rolesToRemove.stream()
                        .map(role -> role.getRole().getName())
                        .toList()
                )
                .bio(member.getBio())
                .stacks(
                        stacksToRemove.stream()
                        .map(stack -> stack.getTechStack().getName())
                        .toList()
                )
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
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
        return new MemberIdDto(member.getEmail(), member.getName(), member.getMemberUUID(),
                member.getProfileImage(), roles, stacks, member.getCreatedAt(), member.getUpdatedAt()
        );
    }

    private MemberResponseDto DtoToResponseDto(MemberInfoDto member){
        return MemberResponseDto.builder()
                .name(member.getName())
                .career(member.getCareer())
                .profileImg(member.getProfileImg())
                .bio(member.getBio())
                .roles(member.getRoles())
                .stacks(member.getStacks())
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .build();
    }
}

