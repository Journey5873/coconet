package com.coconet.memberservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberStackEntityTest {

    @Test
    @DisplayName("Test getMember")
    void getMember() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        TechStackEntity stack = new TechStackEntity(1L, "TestStack","TestCategory", "TestPath");
        MemberStackEntity memberStackEntity = new MemberStackEntity(member, stack);

        Assertions.assertThat(memberStackEntity.getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("Test techStack")
    void getTechStack() {
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        TechStackEntity stack = new TechStackEntity(1L, "TestStack","TestCategory", "TestPath");
        MemberStackEntity memberStackEntity = new MemberStackEntity(member, stack);

        Assertions.assertThat(memberStackEntity.getTechStack()).isEqualTo(stack);
    }
}