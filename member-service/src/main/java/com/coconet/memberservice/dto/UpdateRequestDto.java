package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateRequestDto {
    private Long id;
    private String name;
    private String career;

    public UpdateRequestDto(String name) {
        this.name = name;
    }
}
