package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long>, ChatroomRepositoryCustom{
    ChatRoomEntity findByRoomUUID(UUID roomUUID);
}
