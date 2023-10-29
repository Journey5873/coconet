package com.coconet.articleservice.service;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ArticleSearchCondition;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponseDto updateArticleInfo(Long userId, Long articleId, ArticleRequestDto articleRequestDto) {
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("No article"));
    
        return new ArticleResponseDto();
    }

    public ArticleResponseDto getArticle(Long articleId){
        ArticleFormDto articleFormDto = articleRepository.getArticle(articleId);
        return new ArticleResponseDto(articleFormDto.getTitle(),
                articleFormDto.getContent(),
                articleFormDto.getCreatedAt(),
                articleFormDto.getUpdateAt(),
                articleFormDto.getExpiredAt(),
                articleFormDto.getViewCount(),
                articleFormDto.getBookmarkCount(),
                articleFormDto.getArticleType(),
                articleFormDto.getStatus(),
                articleFormDto.getMeetingType(),
                articleFormDto.getAuthor()
        );
    }

    public Page<ArticleResponseDto> getArticles(ArticleSearchCondition condition, Pageable pageable){
        Page<ArticleFormDto> articleFormDtos = articleRepository.getArticles(condition, pageable);
        Page<ArticleResponseDto> articleResponseDtos = articleFormDtos.map(articleFormDto -> new ArticleResponseDto(articleFormDto.getTitle(),
                articleFormDto.getContent(),
                articleFormDto.getCreatedAt(),
                articleFormDto.getUpdateAt(),
                articleFormDto.getExpiredAt(),
                articleFormDto.getViewCount(),
                articleFormDto.getBookmarkCount(),
                articleFormDto.getArticleType(),
                articleFormDto.getStatus(),
                articleFormDto.getMeetingType(),
                articleFormDto.getAuthor()));
        return  articleResponseDtos;
    }

    public String deleteArticle(Long articleId, Long memberId){
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow();
        if (article.getMember().equals(memberId)){
            articleRepository.deleteById(articleId);
            return "Successfully deleted";
        }else{
            return "Only the author can delete the article";
        }
    }
}
