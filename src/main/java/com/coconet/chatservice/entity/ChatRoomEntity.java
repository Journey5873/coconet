package com.coconet.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "chatroom")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ChatRoomEntity extends BaseEntity{

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chatRoom")
    private List<RooMemberEntity> RoomMembers = new ArrayList<>();

    @Column(columnDefinition = "BINARY(16)", name = "room_uuid")
    private UUID roomUUID;

    @Column(columnDefinition = "BINARY(16)", name = "article_uuid")
    private UUID articleUUID;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid")
    private UUID applicantUUID;

    @Column(name = "room_name")
    private String roomName;
}
