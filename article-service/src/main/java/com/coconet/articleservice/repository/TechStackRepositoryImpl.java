package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.TechStackEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.articleservice.entity.QTechStackEntity.techStackEntity;

@RequiredArgsConstructor
public class TechStackRepositoryImpl implements TechStackRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<TechStackEntity> getTechStacks(List<String> stacks) {
        return queryFactory.selectFrom(techStackEntity)
                .where(techStackEntity.name.in(stacks))
                .fetch();
    }
}