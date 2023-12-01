package com.coconet.articleservice.controller;

import com.coconet.articleservice.common.response.Response;
import com.coconet.articleservice.dto.*;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article-service/api")
@RequiredArgsConstructor
public class PrivateController {

    private final ArticleService articleService;

    @GetMapping("/suggestions")
    public List<ArticleResponseDto> getSuggestions(@RequestHeader(value="memberId") UUID memberUUID){
        return articleService.getSuggestions(memberUUID);
    }


    @PostMapping("/article/create")
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto request, @RequestHeader(value="memberId") UUID memberUUID){
        ArticleResponseDto response = articleService.createArticle(request, memberUUID);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/article")
    public ArticleResponseDto updateArticleInfo(@RequestBody ArticleRequestDto articleRequestDto, @RequestHeader(value="memberId") UUID memberUUID) {
        return articleService.updateArticle(articleRequestDto, memberUUID);
    }

    @DeleteMapping("/delete/{articleUUID}")
    public String deleteArticle(@PathVariable UUID articleUUID, @RequestHeader(value="memberId") UUID memberUUID){
        return articleService.deleteArticle(articleUUID, memberUUID);
    }

    // comment
    @PostMapping("/comment/{articleUUID}")
    public Response<CommentResponseDto> writeComment(
            @PathVariable UUID articleUUID,
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestHeader(value="memberId") UUID memberUUID
    ){
        CommentResponseDto response = articleService.writeComment(commentRequestDto, articleUUID, memberUUID);
        return Response.OK(response);
    }

    @PutMapping("/comment/{commentId}")
    public Response<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestHeader(value="memberId") UUID memberUUID
    ){
        CommentResponseDto response = articleService.updateComment(commentId, commentRequestDto, memberUUID);
        return Response.OK(response);
    }

    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @RequestHeader(value="memberId") UUID memberUUID) {
        articleService.deleteComment(commentId, memberUUID);
    }

    @PostMapping("/bookmark/{articleUUID}")
    public BookmarkResponse updateBookmark(@PathVariable UUID articleUUID, @RequestHeader(value="memberId") UUID memberUUID) {
        return articleService.updateBookmark(articleUUID, memberUUID);
    }

    @GetMapping("/bookmark")
    public List<BookmarkResponse> getBookmarks(@RequestHeader(value="memberId") UUID memberUUID) {
        return articleService.getBookmarks(memberUUID);
    }
 }