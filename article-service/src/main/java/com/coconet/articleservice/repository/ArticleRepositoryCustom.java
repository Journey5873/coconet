package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {
    ArticleFormDto getArticle(String articleUUID);

    Page<ArticleFormDto> getArticles(String keyword, Pageable pageable);
}
