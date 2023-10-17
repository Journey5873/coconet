package com.coconet.articleservice.controller;

import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article-service/open-api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<ArticleResponseDto> updateArticleInfo(@RequestBody() ArticleRequestDto articleRequestDto) {


        ArticleResponseDto articleResponseDto = null;
        return ResponseEntity.status(200).body(articleResponseDto);
    }

}
