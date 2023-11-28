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

    // reply
    @PostMapping("/reply/{articleUUID}")
    public ReplyResponseDto reply(
            @PathVariable UUID articleUUID,
            @RequestBody ReplyRequestDto replyDto,
            @RequestHeader(value="memberId") UUID memberUUID
    ){
        ReplyResponseDto response = articleService.write(replyDto, articleUUID, memberUUID);
        return response;
    }

    @PutMapping("/reply/{replyId}")
    public ReplyResponseDto updateReply(
            @PathVariable Long replyId,
            @RequestBody ReplyRequestDto replyDto,
            @RequestHeader(value="memberId") UUID memberUUID
    ){
        ReplyResponseDto response = articleService.updateReply(replyId, replyDto, memberUUID);

        return response;
    }

    @DeleteMapping("/reply/{replyId}")
    public void deleteReply(@PathVariable Long replyId, @RequestHeader(value="memberId") UUID memberUUID) {
        articleService.deleteReply(replyId, memberUUID);
    }

    @PostMapping("/bookmark/{articleUUID}")
    public Response<BookmarkResponse> updateBookmark(@PathVariable UUID articleUUID, @RequestHeader(value="memberId") UUID memberUUID) {
        BookmarkResponse response = articleService.updateBookmark(articleUUID, memberUUID);
        return Response.OK(response);
    }

    @GetMapping("/bookmark")
    public Response<List<BookmarkResponse>> getBookmarks(@RequestHeader(value="memberId") UUID memberUUID) {
        List<BookmarkResponse> response = articleService.getBookmarks(memberUUID);
        return Response.OK(response);
    }
 }