package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.MemberEntity;
import com.coconet.articleservice.entity.RoleEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.articleservice.entity.QMemberRoleEntity.memberRoleEntity;
import static com.coconet.articleservice.entity.QRoleEntity.roleEntity;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    // TODO refactoring about member-service connecting
    public List<RoleEntity> getMemberRoles(MemberEntity member) {
        return queryFactory.selectFrom(roleEntity)
                .join(memberRoleEntity)
                .on(roleEntity.eq(memberRoleEntity.role))
                .where(memberRoleEntity.member.eq(member))
                .fetch();
    }
}