package com.coconet.chatservice.client;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.client.ArticleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@FeignClient(name = "article-service")
public interface ArticleClient {
    @GetMapping("/chatClient")
    Response<ArticleResponse> sendChatClient(UUID articleUUID);
}
