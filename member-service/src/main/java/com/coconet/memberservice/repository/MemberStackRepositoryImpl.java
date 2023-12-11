package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberStackEntity;
import com.coconet.memberservice.entity.QMemberStackEntity;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.memberservice.entity.QMemberStackEntity.memberStackEntity;

@RequiredArgsConstructor
public class MemberStackRepositoryImpl implements MemberStackRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberStackEntity> getAllStacks(MemberEntity member) {
        return queryFactory.selectFrom(memberStackEntity)
                .where(memberStackEntity.member.eq(member))
                .fetch();
    }
}
