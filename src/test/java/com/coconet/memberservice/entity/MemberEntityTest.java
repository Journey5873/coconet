package com.coconet.memberservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberEntityTest {
    @Test
    @DisplayName("Test Register")
    void register() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getName()).isEqualTo("TestName");
        Assertions.assertThat(member.getCareer()).isEqualTo("1");
    }

    @Test
    @DisplayName("Test changeName")
    void changeName() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getName()).isEqualTo("TestName");

        member.register("AnotherName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        Assertions.assertThat(member.getName()).isEqualTo("AnotherName");
    }

    @Test
    @DisplayName("Test changeCareer")
    void changeCareer() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getCareer()).isEqualTo("1");

        member.register("TestName", "2", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        Assertions.assertThat(member.getCareer()).isEqualTo("2");
    }

    @Test
    @DisplayName("Test profileImage")
    void changeProfileImage() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getProfileImage()).isEqualTo("TestPath");

        member.register("TestName", "2", "AnotherPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        Assertions.assertThat(member.getProfileImage()).isEqualTo("AnotherPath");
    }

    @Test
    @DisplayName("Test githubLink")
    void changeGithubLink() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getGithubLink()).isEqualTo("TestGithub");

        member.register("TestName", "2", "TestPath", "AnotherGithub",
                "TestBlog", "TestNotion", "TestBio");
        Assertions.assertThat(member.getGithubLink()).isEqualTo("AnotherGithub");
    }

    @Test
    @DisplayName("Test blogLink")
    void changeBlogLink() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getBlogLink()).isEqualTo("TestBlog");

        member.register("TestName", "2", "TestPath", "TestGithub",
                "AnotherBlog", "TestNotion", "TestBio");
        Assertions.assertThat(member.getBlogLink()).isEqualTo("AnotherBlog");
    }

    @Test
    @DisplayName("Test notionLink")
    void changeNotionLink() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getNotionLink()).isEqualTo("TestNotion");

        member.register("TestName", "2", "TestPath", "TestGithub",
                "TestBlog", "AnotherNotion", "TestBio");
        Assertions.assertThat(member.getNotionLink()).isEqualTo("AnotherNotion");
    }

    @Test
    @DisplayName("Test bio")
    void changeBio() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getBio()).isEqualTo("TestBio");

        member.register("TestName", "2", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "AnotherBio");
        Assertions.assertThat(member.getBio()).isEqualTo("AnotherBio");
    }

    @Test
    @DisplayName("Test deleteUser")
    void deleteUser() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");

        Assertions.assertThat(member.getIsActivated()).isEqualTo((byte) 1);

        member.deleteUser();
        Assertions.assertThat(member.getIsActivated()).isEqualTo((byte) 0);
    }

}