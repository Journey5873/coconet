package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.ChatRoomEntity;

import java.util.List;
import java.util.UUID;

public interface ChatroomRepositoryCustom {
    List<ChatRoomEntity>  findByAllMemberUUID(UUID memberUUID);

    boolean isMember(UUID memberUUID, UUID roomUUID);
}
