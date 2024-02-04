package com.coconet.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PROTECTED;

@Document(collection = "chat")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatMsgEntity extends BaseEntity{
    @Id
    private String id;
    private String senderUUID;
    private String roomUUID;
    private String message;
}
