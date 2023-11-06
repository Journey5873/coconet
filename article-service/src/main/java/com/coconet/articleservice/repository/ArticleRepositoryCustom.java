package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleSearchCondition;
import com.coconet.articleservice.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    ArticleFormDto getArticle(Long articleId);

    Page<ArticleFormDto> getArticles(ArticleSearchCondition condition, Pageable pageable);
}
