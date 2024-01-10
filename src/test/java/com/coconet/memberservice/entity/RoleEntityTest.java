package com.coconet.memberservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleEntityTest {

    @Test
    void getName() {
        RoleEntity role = RoleEntity.builder()
                .name("TestRole")
                .build();

        Assertions.assertThat(role.getName()).isEqualTo("TestRole");
    }
}