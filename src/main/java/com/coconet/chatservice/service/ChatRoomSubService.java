package com.coconet.chatservice.service;

import com.coconet.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomSubService {
    private final ChatRoomRepository chatRoomRepository;

    public String punctuateTitle(String title) {
        if(title.length() <= 10)
            return title;
        return title.substring(0, 11) + "..";
    }

    public boolean existChatRoom(UUID articleUUID, UUID memberUUID) {
        return chatRoomRepository.existChatRoom(articleUUID, memberUUID);
    }

    public UUID getRoomUUID(UUID articleUUID, UUID memberUUID) {
        return chatRoomRepository.getRoomUUID(articleUUID, memberUUID);
    }
}
