package com.coconet.chatservice.client;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.client.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "member-service")
public interface MemberClient {
    @GetMapping("/member-service/open-api/chatClient/{memberUUID}")
    Response<MemberResponse> sendChatClient(@PathVariable UUID memberUUID);
}
