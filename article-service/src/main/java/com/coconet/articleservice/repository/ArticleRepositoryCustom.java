package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ArticleRepositoryCustom {
    ArticleFormDto getArticle(UUID articleUUID);

    Page<ArticleFormDto> getArticles(String keyword, String articleType,Pageable pageable);
}
