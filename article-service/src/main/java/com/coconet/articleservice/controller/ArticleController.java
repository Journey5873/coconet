package com.coconet.articleservice.controller;

import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ArticleSearchCondition;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article-service/open-api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article/create")
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleRequestDto request
    ){
        ArticleResponseDto response = articleService.createArticle(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/article/{id}")
    public ArticleResponseDto getArticle(@PathVariable String id){
        ArticleResponseDto article = articleService.getArticle(Long.valueOf(id));
        return article;
    }

    @GetMapping("/articles")
    public Page<ArticleResponseDto> getArticles(@RequestParam String keyword, Pageable pageable){
        return articleService.getArticles(keyword, pageable);
    }

    @PostMapping("/article")
    public ResponseEntity<ArticleResponseDto> updateArticleInfo(@RequestBody() ArticleRequestDto articleRequestDto) {
        ArticleResponseDto articleResponseDto = null;
        return ResponseEntity.status(200).body(articleResponseDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable String id){
        Long memberId = 2L;
        return articleService.deleteArticle(Long.valueOf(id), memberId);
    }
}
