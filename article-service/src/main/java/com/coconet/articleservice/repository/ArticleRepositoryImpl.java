package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.dto.ArticleSearchCondition;
import com.coconet.articleservice.dto.QArticleFormDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.coconet.articleservice.entity.QArticleEntity.articleEntity;
import static com.coconet.articleservice.entity.QMemberEntity.memberEntity;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ArticleFormDto getArticle(Long articleId) {
        return queryFactory
                .select(new QArticleFormDto(
                        articleEntity.title,
                        articleEntity.content,
                        articleEntity.createdAt,
                        articleEntity.updatedAt,
                        articleEntity.expiredAt,
                        articleEntity.viewCount,
                        articleEntity.bookmarkCount,
                        articleEntity.articleType,
                        articleEntity.status,
                        articleEntity.meetingType,
                        articleEntity.member.name
                ))
                .from(articleEntity)
                .leftJoin(articleEntity.member, memberEntity)
                .where(articleEntity.id.eq(articleId))
                .fetchOne();
    }

    @Override
    public Page<ArticleFormDto> getArticles(ArticleSearchCondition condition, Pageable pageable) {
        List<ArticleFormDto> results = queryFactory.select(new QArticleFormDto(
                        articleEntity.title,
                        articleEntity.content,
                        articleEntity.createdAt,
                        articleEntity.updatedAt,
                        articleEntity.expiredAt,
                        articleEntity.viewCount,
                        articleEntity.bookmarkCount,
                        articleEntity.articleType,
                        articleEntity.status,
                        articleEntity.meetingType,
                        articleEntity.member.name
                )).from(articleEntity)
                .leftJoin(articleEntity.member, memberEntity)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ArticleEntity> countQuery = queryFactory
                .selectFrom(articleEntity)
                .where(titleContains(condition.getTitle()),
                        contentContains(condition.getContent()));

        return PageableExecutionUtils.getPage(results, pageable, () -> countQuery.fetchCount());
    }

    private BooleanExpression titleContains(String title){
        return isEmpty(title) ? null : articleEntity.title.contains(title);
    }

    private BooleanExpression contentContains(String content){
        return isEmpty(content) ? null : articleEntity.content.contains(content);
    }
}
