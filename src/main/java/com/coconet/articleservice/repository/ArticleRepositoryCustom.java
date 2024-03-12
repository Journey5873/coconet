package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.RoleEntity;
import com.coconet.articleservice.entity.TechStackEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.MeetingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ArticleRepositoryCustom {
    ArticleEntity getArticle(UUID articleUUID);

    Page<ArticleResponseDto> getArticles(
            List<RoleEntity> roles, List<TechStackEntity> stacks, String keyword,
            ArticleType articleType, MeetingType meetingType, boolean bookmark, UUID memberUUID, Pageable pageable
    );
    List<ArticleResponseDto> getSuggestions(List<RoleEntity> roleEntities, List<TechStackEntity> stackEntities);

    List<ArticleResponseDto> getPopularPosts();

    Page<ArticleResponseDto> getMyArticles(UUID memberUUID, Pageable pageable);
}
