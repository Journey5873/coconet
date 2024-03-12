package com.coconet.memberservice.controller;

import com.coconet.memberservice.dto.MemberRequestDto;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.service.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrivateMemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PrivateMemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MemberServiceImpl memberService;

    List<String> roles = new ArrayList<>();
    List<String> stacks = new ArrayList<>();

    @Test
    @WithMockUser
    void getUserInfo() throws Exception {
        //given
        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");

        roles.add("Backend");
        stacks.add("Java");
        MemberResponseDto response = new MemberResponseDto("TestName", 5, "TestProfile".getBytes(),
                "TestBio", "TestGit", "TestBlog", "TestNotion", roles, stacks);

        given(memberService.getUserInfo(memberUUID)).willReturn(response);

        mockMvc.perform(
                        get("/member-service/api/my-profile")
                                .header("memberUUID", memberUUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("TestName"))
                .andExpect(jsonPath("$.data.career").value(5))
//                .andExpect(jsonPath("$.data.profileImg").value("TestProfile"))
                .andExpect(jsonPath("$.data.bio").value("TestBio"))
                .andExpect(jsonPath("$.data.githubLink").value("TestGit"))
                .andExpect(jsonPath("$.data.blogLink").value("TestBlog"))
                .andExpect(jsonPath("$.data.notionLink").value("TestNotion"))
                .andExpect(jsonPath("$.data.roles").isArray())
                .andExpect(jsonPath("$.data.roles").value(roles))
                .andExpect(jsonPath("$.data.stacks").isArray())
                .andExpect(jsonPath("$.data.stacks").value(stacks))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void updateUserInfo() throws Exception {
        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");
        roles.add("Backend");
        stacks.add("Java");
        MemberRequestDto request = new MemberRequestDto("TestName", 5,
                roles, stacks, "TestBio", "TestGit", "TestBlog", "TestNotion");
        MemberResponseDto response = new MemberResponseDto("ChangeName", 7, "ChangeProfile".getBytes(),
                "ChangeBio", "ChangeGit", "ChangeBlog", "ChangeNotion", roles, stacks);
        given(memberService.updateUserInfo(any(UUID.class), any(MemberRequestDto.class), any(MultipartFile.class)))
                .willReturn(response);

        MockMultipartFile requestDto = new MockMultipartFile("requestDto", "", "application/json", objectMapper.writeValueAsString(request).getBytes());
        MockMultipartFile image = new MockMultipartFile("imageFile", "", "png", "<<data>>".getBytes());

        mockMvc.perform(
                multipart(HttpMethod.PUT, "/member-service/api/my-profile")
                        .file(requestDto)
                        .file(image)
                .header("memberUUID", memberUUID)
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("ChangeName"))
                .andExpect(jsonPath("$.data.career").value(7))
//                .andExpect(jsonPath("$.data.profileImg").value("ChangeProfile"))
                .andExpect(jsonPath("$.data.bio").value("ChangeBio"))
                .andExpect(jsonPath("$.data.githubLink").value("ChangeGit"))
                .andExpect(jsonPath("$.data.blogLink").value("ChangeBlog"))
                .andExpect(jsonPath("$.data.notionLink").value("ChangeNotion"))
                .andExpect(jsonPath("$.data.roles").isArray())
                .andExpect(jsonPath("$.data.roles").value(roles))
                .andExpect(jsonPath("$.data.stacks").isArray())
                .andExpect(jsonPath("$.data.stacks").value(stacks))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void deleteUser() throws Exception {
        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");
        roles.add("Backend");
        stacks.add("Java");
        MemberResponseDto response = new MemberResponseDto("TestName", 5, "TestProfile".getBytes(),
                "TestBio", "TestGit", "TestBlog", "TestNotion", roles, stacks);

        given(memberService.deleteUser(memberUUID));

        mockMvc.perform(
                delete("/member-service/api/delete")
                .header("memberUUID", memberUUID)
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("TestName"))
                .andExpect(jsonPath("$.data.career").value(5))
//                .andExpect(jsonPath("$.data.profileImg").value("TestProfile"))
                .andExpect(jsonPath("$.data.bio").value("TestBio"))
                .andExpect(jsonPath("$.data.githubLink").value("TestGit"))
                .andExpect(jsonPath("$.data.blogLink").value("TestBlog"))
                .andExpect(jsonPath("$.data.notionLink").value("TestNotion"))
                .andExpect(jsonPath("$.data.roles").isArray())
                .andExpect(jsonPath("$.data.roles").value(roles))
                .andExpect(jsonPath("$.data.stacks").isArray())
                .andExpect(jsonPath("$.data.stacks").value(stacks))
                .andDo(print());
    }
}