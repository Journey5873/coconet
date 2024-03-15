package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.ChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepositoryCustom {
    Page<ChatRoomEntity> findByAllMemberUUID(UUID memberUUID, Pageable pageable);

    boolean isMember(UUID memberUUID, UUID roomUUID);

    boolean existChatRoom(UUID articleUUID, UUID memberUUID);

    UUID getRoomUUID(UUID articleUUID, UUID memberUUID);
}