package com.coconet.articleservice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class ArticleRoleDto {
    private String roleName;
    private Integer participant;

    @QueryProjection
    public ArticleRoleDto(String roleName, Integer participant) {
        this.roleName = roleName;
        this.participant = participant;
    }
}
