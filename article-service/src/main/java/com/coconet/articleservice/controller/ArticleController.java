package com.coconet.articleservice.controller;

import com.coconet.articleservice.dto.ArticleRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ReplyRequestDto;
import com.coconet.articleservice.dto.ReplyResponseDto;
import com.coconet.articleservice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article-service/open-api")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/article/create")
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto request){
        ArticleResponseDto response = articleService.createArticle(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/article/{articleUUID}")
    public ArticleResponseDto getArticle(@PathVariable String articleUUID){
        return articleService.getArticle(articleUUID);
    }

    @GetMapping("/articles")
    public Page<ArticleResponseDto> getArticles(String keyword, String articleType, Pageable pageable){
        return articleService.getArticles(keyword, articleType, pageable);
    }

    @GetMapping("/suggestions")
    public List<ArticleResponseDto> getSuggestions(){
        return articleService.getSuggestions();
    }

    @GetMapping("/popular")
    public List<ArticleResponseDto> getPopularPosts(){ return articleService.getPopularPosts(); }

    @PutMapping("/article")
    public ArticleResponseDto updateArticleInfo(@RequestBody() ArticleRequestDto articleRequestDto) {
        return articleService.updateArticle(articleRequestDto);
    }

    @DeleteMapping("/delete/{articleUUID}")
    public String deleteArticle(@PathVariable String articleUUID){
        return articleService.deleteArticle(articleUUID);
    }

    // reply

    @PostMapping("/reply/{articleUUID}")
    public ReplyResponseDto reply(
            @PathVariable String articleUUID,
            @RequestBody ReplyRequestDto replyDto
    ){
        ReplyResponseDto response = articleService.write(replyDto, articleUUID);
        return response;
    }

    @PutMapping("/reply/{replyId}")
    public ReplyResponseDto updateReply(
            @PathVariable Long replyId,
            @RequestBody ReplyRequestDto replyDto
    ){
        Long memberId = 1L;
        ReplyResponseDto response = articleService.updateReply(replyId, replyDto, memberId);

        return response;
    }

    @DeleteMapping("/reply/{replyId}")
    public void deleteReply(@PathVariable Long replyId) {
        Long memberId = 1L;
        articleService.deleteReply(replyId, memberId);
    }
}
