package com.coconet.articleservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
public class ArticleRoleUpdateDto {
    private Long articleRoleId;
    private Long roleId;
    private String roleName;
    private Integer participant;

    public ArticleRoleUpdateDto(Long articleRoleId, Long roleId,
                                String roleName, Integer participant) {
        this.articleRoleId = articleRoleId;
        this.roleId = roleId;
        this.roleName = roleName;
        this.participant = participant;
    }

    public ArticleRoleUpdateDto(Long roleId, String roleName, Integer participant) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.participant = participant;
    }
}
