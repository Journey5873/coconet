package com.coconet.memberservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberStackResponse{
    private String name;
    private String image;
    private String category;
}
