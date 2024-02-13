package com.coconet.articleservice.controller;

import com.coconet.articleservice.common.response.Response;
import com.coconet.articleservice.dto.*;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article-service/api")
@RequiredArgsConstructor
public class PrivateArticleController {

    private final ArticleService articleService;

    @GetMapping("/article/{articleUUID}")
    public Response<ArticleResponseDto> getArticle(@PathVariable String articleUUID,
                                                   @RequestHeader(value = "memberUUID") UUID memberUUID){
        ArticleResponseDto article = articleService.getArticle(articleUUID, memberUUID);
        return Response.OK(article);
    }

    @PostMapping("/articles")
    public Response<Page<ArticleResponseDto>> getArticles(@RequestBody ArticleFilterDto condition,
                                                @RequestHeader(value = "memberUUID") UUID memberUUID,
                                                Pageable pageable
    ){
        return Response.OK(articleService.getArticles(condition, memberUUID, pageable));
    }

    @GetMapping("/my-articles")
    public Response<Page<ArticleResponseDto>> getMyArticles(
            @RequestHeader(value = "memberUUID") UUID memberUUID,
            Pageable pageable
    ){
        return Response.OK(articleService.getMyArticles(memberUUID, pageable));
    }

    @GetMapping("/suggestions")
    public Response<List<ArticleResponseDto>> getSuggestions(@RequestHeader(value="memberUUID") UUID memberUUID){
        return Response.OK(articleService.getSuggestions(memberUUID));
    }


    @PostMapping("/article/create")
    public Response<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto request, @RequestHeader(value="memberUUID") UUID memberUUID){
        ArticleResponseDto response = articleService.createArticle(request, memberUUID);
        return Response.OK(response);
    }

    @PutMapping("/article")
    public Response<ArticleResponseDto> updateArticleInfo(@RequestBody ArticleRequestDto articleRequestDto, @RequestHeader(value="memberUUID") UUID memberUUID) {
        return Response.OK(articleService.updateArticle(articleRequestDto, memberUUID));
    }

    @PutMapping("/delete/{articleUUID}")
    public Response<String> deleteArticle(@PathVariable UUID articleUUID, @RequestHeader(value="memberUUID") UUID memberUUID){
        return Response.OK(articleService.deleteArticle(articleUUID, memberUUID));
    }

    @PostMapping("/bookmark/{articleUUID}")
    public Response<BookmarkResponse> updateBookmark(@PathVariable UUID articleUUID, @RequestHeader(value="memberUUID") UUID memberUUID) {
        return Response.OK(articleService.updateBookmark(articleUUID, memberUUID));
    }

    @GetMapping("/bookmark")
    public Response<List<BookmarkResponse>> getBookmarks(@RequestHeader(value="memberUUID") UUID memberUUID) {
        return Response.OK(articleService.getBookmarks(memberUUID));
    }
 }