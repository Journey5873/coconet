package com.coconet.articleservice.controller;

import com.coconet.articleservice.dto.ArticleFilterDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public Page<ArticleResponseDto> getArticles(@RequestBody ArticleFilterDto condition,
                                                Pageable pageable){
        return articleService.getArticles(condition, null, pageable);
    }

    @GetMapping("/popular")
    public List<ArticleResponseDto> getPopularPosts(){ return articleService.getPopularPosts(); }
}
