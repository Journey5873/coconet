package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.RooMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.coconet.chatservice.entity.QChatRoomEntity.chatRoomEntity;
import static com.coconet.chatservice.entity.QRooMemberEntity.rooMemberEntity;

@RequiredArgsConstructor
public class RoomMemberRepositoryImpl implements RoomMemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RooMemberEntity> getMyRooms(UUID memberUUID) {
        List<RooMemberEntity> rooMemberEntities = jpaQueryFactory.selectFrom(rooMemberEntity)
                .leftJoin(rooMemberEntity.chatRoom, chatRoomEntity)
                .where(rooMemberEntity.memberUUID.eq(memberUUID))
                .fetch();

        System.out.println(rooMemberEntities.size());

        return rooMemberEntities;
    }
}
