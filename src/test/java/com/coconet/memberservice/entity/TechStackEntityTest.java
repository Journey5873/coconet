package com.coconet.memberservice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechStackEntityTest {

    @Test
    void getName() {
        TechStackEntity techStack = TechStackEntity.builder()
                .name("TestStack")
                .category("Backend")
                .image("TestPath")
                .build();

        Assertions.assertThat(techStack.getName()).isEqualTo("TestStack");
    }

    @Test
    void getCategory() {
        TechStackEntity techStack = TechStackEntity.builder()
                .name("TestStack")
                .category("Backend")
                .image("TestPath")
                .build();

        Assertions.assertThat(techStack.getCategory()).isEqualTo("Backend");
    }

    @Test
    void getImage() {
        TechStackEntity techStack = TechStackEntity.builder()
                .name("TestStack")
                .category("Backend")
                .image("TestPath")
                .build();

        Assertions.assertThat(techStack.getImage()).isEqualTo("TestPath");
    }
}