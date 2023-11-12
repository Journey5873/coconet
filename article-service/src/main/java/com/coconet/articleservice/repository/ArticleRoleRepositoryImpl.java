package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleRoleEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static com.coconet.articleservice.entity.QArticleRoleEntity.articleRoleEntity;
import static com.coconet.articleservice.entity.QRoleEntity.roleEntity;

@RequiredArgsConstructor
public class ArticleRoleRepositoryImpl implements ArticleRoleRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ArticleRoleEntity> getArticleRoles(ArticleEntity article) {
        return queryFactory
                .select(articleRoleEntity)
                .from(articleRoleEntity)
                .join(articleRoleEntity.role, roleEntity)
                .fetchJoin()
                .where(articleRoleEntity.article.eq(article))
                .fetch();
    }

    @Override
    public Long deleteArticleRolesIn(List<Long> rolesId) {
        Long count = queryFactory
                .delete(articleRoleEntity)
                .where(articleRoleEntity.id.in(rolesId))
                .execute();
        return count;
    }
}
