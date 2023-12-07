package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.entity.RoleEntity;
import com.coconet.articleservice.entity.TechStackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ArticleRepositoryCustom {
    ArticleFormDto getArticle(UUID articleUUID);

    Page<ArticleFormDto> getArticles(
            List<RoleEntity> roles, List<TechStackEntity> stacks, String keyword,
            String articleType, String meetingType, boolean bookmark, UUID memberUUID, Pageable pageable
    );
    List<ArticleFormDto> getSuggestions(List<RoleEntity> roleEntities, List<TechStackEntity> stackEntities);

    List<ArticleFormDto> getPopularPosts();
}
