package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.MemberEntity;
import com.coconet.articleservice.entity.TechStackEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.articleservice.entity.QMemberRoleEntity.memberRoleEntity;
import static com.coconet.articleservice.entity.QMemberStackEntity.memberStackEntity;
import static com.coconet.articleservice.entity.QTechStackEntity.techStackEntity;

@RequiredArgsConstructor
public class TechStackRepositoryImpl implements TechStackRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    // TODO refactoring about member-service connecting
    public List<TechStackEntity> getMemberStacks(MemberEntity member) {
        return queryFactory.selectFrom(techStackEntity)
                .join(memberStackEntity)
                .on(techStackEntity.eq(memberStackEntity.techStack))
                .where(memberStackEntity.member.eq(member))
                .fetch();
    }
}