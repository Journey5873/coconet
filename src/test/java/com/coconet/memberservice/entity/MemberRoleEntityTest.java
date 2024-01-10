package com.coconet.memberservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberRoleEntityTest {

    @Test
    @DisplayName("Test MemberRoleEntity")
    void memberRoleEntity(){
        MemberEntity member = new MemberEntity();
        member.register("TestName", "1", "TestPath", "TestGithub",
                "TestBlog", "TestNotion", "TestBio");
        RoleEntity role = new RoleEntity(1L, "TestRole");
        MemberRoleEntity memberRoleEntity = new MemberRoleEntity(member, role);

        Assertions.assertThat(memberRoleEntity.getMember()).isEqualTo(member);
        Assertions.assertThat(memberRoleEntity.getRole()).isEqualTo(role);

    }

}