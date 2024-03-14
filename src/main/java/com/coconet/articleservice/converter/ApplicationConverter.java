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
                .applicantUUID(applicationEntity.getApplicantUUID())
                .articleName(applicationEntity.getArticle().getTitle())
                .applicationDate(applicationEntity.getCreatedAt())
                .build();
    }

    public static ApplicationEntity converterToEntity(UUID memberUUID, ArticleEntity article) {
        return ApplicationEntity.builder()
                .applicationUUID(UUID.randomUUID())
                .applicantUUID(memberUUID)
                .article(article)
                .build();
    }
}
