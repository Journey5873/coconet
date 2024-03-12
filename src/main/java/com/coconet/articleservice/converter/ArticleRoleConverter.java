package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.entity.ArticleRoleEntity;
import lombok.Builder;

@Builder
public class ArticleRoleConverter {

    public static ArticleRoleDto convertToDto(ArticleRoleEntity articleRoleEntity) {
        return ArticleRoleDto.builder()
                .roleName(articleRoleEntity.getRole().getName())
                .participant(articleRoleEntity.getParticipant())
                .build();
    }
}