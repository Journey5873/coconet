package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    ArticleFormDto getArticle(String articleId);

    Page<ArticleFormDto> getArticles(String keyword, Pageable pageable);
}
