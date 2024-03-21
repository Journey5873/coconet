package com.coconet.chatservice.repository;


import com.coconet.chatservice.entity.ChatRoomEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.UUID;

import static com.coconet.chatservice.entity.QChatRoomEntity.chatRoomEntity;

@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ChatRoomEntity> findByAllMemberUUID(UUID memberUUID, Pageable pageable) {
        List<ChatRoomEntity> contents = queryFactory.selectFrom(chatRoomEntity)
                .where(chatRoomEntity.applicantUUID.eq(memberUUID).or(chatRoomEntity.writerUUID.eq(memberUUID)))
                .fetch();

        JPAQuery<ChatRoomEntity> countQuery = queryFactory.selectFrom(chatRoomEntity)
                .where(chatRoomEntity.applicantUUID.eq(memberUUID).or(chatRoomEntity.writerUUID.eq(memberUUID)));

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public boolean isMember(UUID memberUUID, UUID roomUUID) {
        ChatRoomEntity chatroomEntity = queryFactory.selectFrom(chatRoomEntity)
                .where((chatRoomEntity.applicantUUID.eq(memberUUID).or(chatRoomEntity.writerUUID.eq(memberUUID))
                        .and(chatRoomEntity.roomUUID.eq(roomUUID))))
                .fetchFirst();

        return chatroomEntity != null;
    }

    @Override
    public boolean existChatRoom(UUID articleUUID, UUID memberUUID) {
        ChatRoomEntity chatroomEntity = queryFactory.selectFrom(chatRoomEntity)
                .where(chatRoomEntity.articleUUID.eq(articleUUID).and(chatRoomEntity.applicantUUID.eq(memberUUID)))
                .fetchFirst();

        return chatroomEntity != null;
    }

    @Override
    public UUID getRoomUUID(UUID articleUUID, UUID memberUUID) {
        ChatRoomEntity chatroomEntity = queryFactory.selectFrom(chatRoomEntity)
                .where(chatRoomEntity.articleUUID.eq(articleUUID).and(chatRoomEntity.applicantUUID.eq(memberUUID)))
                .fetchFirst();

        return chatroomEntity.getRoomUUID();
    }
}