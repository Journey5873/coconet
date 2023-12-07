package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.RoleEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.articleservice.entity.QRoleEntity.roleEntity;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<RoleEntity> getRoles(List<String> roles) {
        return queryFactory.selectFrom(roleEntity)
                .where(roleEntity.name.in(roles))
                .fetch();
    }
}