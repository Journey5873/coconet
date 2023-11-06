package com.coconet.articleservice.service;

import com.coconet.articleservice.dto.*;
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

    public ArticleResponseDto getArticle(Long articleId){
        ArticleFormDto articleFormDto = articleRepository.getArticle(articleId);
        return buildArticleResponseDto(articleFormDto);
    }

    public Page<ArticleResponseDto> getArticles(ArticleSearchCondition condition, Pageable pageable){
        Page<ArticleFormDto> articleFormDtos = articleRepository.getArticles(condition, pageable);
        Page<ArticleResponseDto> articleResponseDtos = articleFormDtos.map(articleFormDto ->
                        buildArticleResponseDto(articleFormDto)
                );
        return  articleResponseDtos;
    }

    public ArticleResponseDto updateArticle(ArticleRoleDto articleRoleDto, Long articleId){
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Not found article"));


        return null;
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

    private ArticleResponseDto buildArticleResponseDto(ArticleFormDto articleFormDto){
        return  ArticleResponseDto.builder()
                .articleId(articleFormDto.getArticleId())
                .title(articleFormDto.getTitle())
                .content(articleFormDto.getContent())
                .createdAt(articleFormDto.getCreatedAt())
                .updateAt(articleFormDto.getUpdateAt())
                .plannedStartAt(articleFormDto.getPlannedStartAt())
                .expiredAt(articleFormDto.getExpiredAt())
                .estimatedDuration(articleFormDto.getEstimatedDuration())
                .viewCount(articleFormDto.getViewCount())
                .bookmarkCount(articleFormDto.getBookmarkCount())
                .articleType(articleFormDto.getArticleType())
                .status(articleFormDto.getStatus())
                .meetingType(articleFormDto.getMeetingType())
                .author(articleFormDto.getAuthor())
                .articleRoleDtos(articleFormDto.getArticleRoleDtos())
                .articleStackDtos(articleFormDto.getArticleStackDtos())
                .build();
    }
}
