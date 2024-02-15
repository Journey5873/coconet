package com.coconet.memberservice.controller;

import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.dto.client.MemberClientDto;
import com.coconet.memberservice.dto.client.MemberRoleResponse;
import com.coconet.memberservice.dto.client.MemberStackResponse;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import com.coconet.memberservice.service.MemberServiceImpl;
import com.coconet.memberservice.service.MemberTokenService;
import com.coconet.memberservice.service.MemberValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OpenMemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class OpenMemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MemberServiceImpl memberService;
    @MockBean
    MemberValidationService memberValidationService;
    @MockBean
    MemberTokenService memberTokenService;

    @Test
    @WithMockUser
    @DisplayName("Register with only necessary info")
    void registerWithNecessary() throws Exception {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("Backend");
        List<String> stacks = new ArrayList<>();
        stacks.add("Java");

        MemberRegisterRequestDto request =
                new MemberRegisterRequestDto(UUID.fromString("31323361-7364-0000-0000-000000000000"),
                        "TestName", 10, roles, stacks, null, null, null, null);

        given(memberService.register(any(MemberRegisterRequestDto.class), any(MockMultipartFile.class)))
                .willReturn(TokenResponse.builder()
                .accessToken("Token")
                .memberUUID(UUID.fromString("31323361-7364-0000-0000-000000000000"))
                .build());

        MockMultipartFile requestDto = new MockMultipartFile("requestDto", "", "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));
        MockMultipartFile image = new MockMultipartFile("imageFile", "", "png", "<<data>>".getBytes());

        mockMvc.perform(
                        multipart("/member-service/open-api/register")
                                .file(requestDto)
                                .file(image)
                                .contentType(MULTIPART_FORM_DATA)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("Token"))
                .andExpect(jsonPath("$.data.memberUUID").value("31323361-7364-0000-0000-000000000000"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void getMemberId() throws Exception {
        MemberRoleResponse memberRoleResponse = MemberRoleResponse.builder()
                .name("Backend")
                .build();
        MemberStackResponse memberStackResponse = MemberStackResponse.builder().name("Java").image("imagePath")
                .category("Category")
                .build();
        List<MemberRoleResponse> roles = new ArrayList<>();
        List<MemberStackResponse> stacks = new ArrayList<>();
        roles.add(memberRoleResponse);
        stacks.add(memberStackResponse);


        given(memberService.clientMemberAllInfo(UUID.fromString("31323361-7364-0000-0000-000000000000"))).willReturn(
                new MemberClientDto("Email", "Name", UUID.fromString("31323361-7364-0000-0000-000000000000"),
                "ProfilePath", roles, stacks, LocalDateTime.now(), LocalDateTime.now()
        ));

        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");

        mockMvc.perform(
                        get("/member-service/open-api/" + memberUUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("Email"))
                .andExpect(jsonPath("$.data.name").value("Name"))
                .andExpect(jsonPath("$.data.memberUUID").value("31323361-7364-0000-0000-000000000000"))
                .andExpect(jsonPath("$.data.profileImage").value("ProfilePath"))
                .andExpect(jsonPath("$.data.roles").isArray())
                .andExpect(jsonPath("$.data.stacks").isArray())
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("Case: Not exist")
    void checkMemberNickName() throws Exception {
        given(memberService.checkMemberNickName("notExist")).willReturn(Boolean.TRUE);

        String name = "notExist";
        mockMvc.perform(
                        get("/member-service/open-api/memberNameCheck/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(Boolean.TRUE))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("Case: exist")
    void checkMemberNickNameWithExistName() throws Exception {
        given(memberService.checkMemberNickName("exist")).willReturn(Boolean.FALSE);

        String name = "exist";
        mockMvc.perform(
                        get("/member-service/open-api/memberNameCheck/" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(Boolean.FALSE))
                .andDo(print());
    }
}