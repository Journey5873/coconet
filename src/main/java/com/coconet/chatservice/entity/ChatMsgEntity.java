package com.coconet.chatservice.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Document(collection = "chat")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatMsgEntity extends BaseEntity{

    @Id
    private String id;
    private UUID senderUUID;
    private UUID roomUUID;
    private String message;
}
