package com.coconet.chatservice.mongo;

import com.coconet.chatservice.entity.ChatMsgEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMsgRepository extends MongoRepository<ChatMsgEntity, String> {
    List<ChatMsgEntity> findAllByRoomUUID(String uuid);
}
