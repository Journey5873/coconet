package com.coconet.chatservice.mongo;

import com.coconet.chatservice.entity.ChatMsgEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMsgRepository extends MongoRepository<ChatMsgEntity, String> {
    List<ChatMsgEntity> findAllByRoomUUID(UUID roomUUID);

    void deleteAllByRoomUUID(UUID roomUUID);
}
