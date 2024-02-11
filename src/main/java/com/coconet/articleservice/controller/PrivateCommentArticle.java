package com.coconet.articleservice.controller;

import com.coconet.articleservice.common.response.Response;
import com.coconet.articleservice.dto.CommentRequestDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article-service/api")
public class PrivateCommentArticle {
    private final ArticleService articleService;

    @PostMapping("/comment/{articleUUID}")
    public Response<CommentResponseDto> writeComment(
            @PathVariable UUID articleUUID,
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestHeader(value="memberUUID") UUID memberUUID
    ){
        CommentResponseDto response = articleService.writeComment(commentRequestDto, articleUUID, memberUUID);
        return Response.OK(response);
    }

    @PutMapping("/comment")
    public Response<CommentResponseDto> updateComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestHeader(value="memberUUID") UUID memberUUID
    ){
        CommentResponseDto response = articleService.updateComment(commentRequestDto, memberUUID);
        return Response.OK(response);
    }

    @DeleteMapping("/comment")
    public void deleteComment(@RequestBody CommentRequestDto commentRequestDto, @RequestHeader(value="memberUUID") UUID memberUUID) {
        articleService.deleteComment(commentRequestDto.getCommentUUID(), memberUUID);
    }
}
