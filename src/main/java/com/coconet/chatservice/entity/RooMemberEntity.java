package com.coconet.chatservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "room_member")
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RooMemberEntity extends  BaseEntity{

    @Id
    @Column(name = "room_member_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoomEntity chatRoom;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid")
    private UUID memberUUID;
}
