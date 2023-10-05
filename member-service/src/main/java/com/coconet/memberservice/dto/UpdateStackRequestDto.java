package com.coconet.memberservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateStackRequestDto {

    private Long memberId;
    private List<Long> stackIds;
}
