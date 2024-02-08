package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.ChatRoomEntity;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepositoryCustom {
    List<ChatRoomEntity>  findByAllMemberUUID(UUID memberUUID);

    boolean isMember(UUID memberUUID, UUID roomUUID);

    boolean existChatRoom(UUID articleUUID, UUID memberUUID);

    UUID getRoomUUID(UUID articleUUID, UUID memberUUID);
}
