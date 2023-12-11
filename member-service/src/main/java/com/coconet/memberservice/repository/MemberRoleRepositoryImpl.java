package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.memberservice.entity.QMemberEntity.memberEntity;
import static com.coconet.memberservice.entity.QMemberRoleEntity.*;
import static com.coconet.memberservice.entity.QRoleEntity.roleEntity;

@RequiredArgsConstructor
public class MemberRoleRepositoryImpl implements MemberRoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberRoleEntity> getAllRoles(MemberEntity member) {
        return queryFactory.selectFrom(memberRoleEntity)
                .where(memberRoleEntity.member.eq(member))
                .fetch();
    }
}
