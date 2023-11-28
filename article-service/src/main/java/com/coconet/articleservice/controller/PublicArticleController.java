package com.coconet.articleservice.controller;

import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article-service/open-api")
@RequiredArgsConstructor
public class PublicArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/{articleUUID}")
    public ArticleResponseDto getArticle(@PathVariable String articleUUID){
        ArticleResponseDto article = articleService.getArticle(articleUUID);
        return article;
    }

    @GetMapping("/articles")
    public Page<ArticleResponseDto> getArticles(String keyword, String articleType, Pageable pageable){
        return articleService.getArticles(keyword, articleType, pageable);
    }

    @GetMapping("/popular")
    public List<ArticleResponseDto> getPopularPosts(){ return articleService.getPopularPosts(); }
}
