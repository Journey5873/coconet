package com.coconet.articleservice.service;

import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponseDto updateArticleInfo(Long userId, Long articleId, ArticleRequestDto articleRequestDto) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("No article"));


    }
}
