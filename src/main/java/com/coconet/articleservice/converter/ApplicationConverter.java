package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.ApplicationDto;
import com.coconet.articleservice.entity.ApplicationEntity;
import com.coconet.articleservice.entity.ArticleEntity;

import java.util.UUID;

public class ApplicationConverter {

    public static ApplicationDto converterToDto(ApplicationEntity applicationEntity) {
        return ApplicationDto.builder()
                .applicationUUID(applicationEntity.getApplicationUUID())
                .articleUUID(applicationEntity.getArticle().getArticleUUID())
                .applicantUUID(applicationEntity.getArticle().getMemberUUID())
                .articleName(applicationEntity.getArticle().getTitle())
                .applicationDate(applicationEntity.getCreatedAt())
                .applicationPosition(applicationEntity.getApplicationPosition())
                .build();
    }

    public static ApplicationEntity converterToEntity(ApplicationDto applicationDto, UUID memberUUID, ArticleEntity article) {
        return ApplicationEntity.builder()
                .applicationUUID(UUID.randomUUID())
                .memberUUID(memberUUID)
                .article(article)
                .applicationPosition(applicationDto.getApplicationPosition())
                .build();
    }
}
