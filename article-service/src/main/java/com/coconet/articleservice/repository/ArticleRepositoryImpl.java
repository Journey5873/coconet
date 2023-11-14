package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.dto.ArticleStackDto;
import com.coconet.articleservice.entity.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.coconet.articleservice.entity.QArticleEntity.articleEntity;
import static com.coconet.articleservice.entity.QArticleRoleEntity.articleRoleEntity;
import static com.coconet.articleservice.entity.QArticleStackEntity.articleStackEntity;
import static com.coconet.articleservice.entity.QMemberEntity.memberEntity;
import static com.coconet.articleservice.entity.QMemberRoleEntity.memberRoleEntity;
import static com.coconet.articleservice.entity.QMemberStackEntity.memberStackEntity;
import static com.coconet.articleservice.entity.QRoleEntity.roleEntity;
import static com.coconet.articleservice.entity.QTechStackEntity.techStackEntity;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public ArticleFormDto getArticle(String articleUUID) {

        ArticleEntity article = queryFactory
                .selectFrom(articleEntity)
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .leftJoin(articleEntity.member, memberEntity)
                .where(articleEntity.articleUUID.eq(UUID.fromString(articleUUID)))
                .fetchOne();

        if (article != null) {
            return buildArticleFormDto(article);
        }
        return null;
    }

    @Override
    public Page<ArticleFormDto> getArticles(String keyword, Pageable pageable) {
        List<ArticleEntity> articles = queryFactory
                .selectFrom(articleEntity)
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .orderBy(articleEntity.createdAt.desc())
                .distinct()
                .where(titleContains(keyword),
                        (contentContains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ArticleFormDto> contents = articles.stream()
                .map(article -> buildArticleFormDto(article))
                .toList();

        JPAQuery<ArticleEntity> countQuery = queryFactory
                .selectFrom(articleEntity)
                .where(
                        titleContains(keyword),
                        contentContains(keyword)
                );

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetchCount());
    }

    private BooleanExpression titleContains(String title){
        return isEmpty(title) ? null : articleEntity.title.contains(title);
    }

    private BooleanExpression contentContains(String content){
        return isEmpty(content) ? null : articleEntity.content.contains(content);
    }

    private List<ArticleRoleDto> getArticleRoles(ArticleEntity article){
        return queryFactory
                .select(Projections.constructor(ArticleRoleDto.class,
                        articleRoleEntity.role.name,
                        articleRoleEntity.participant))
                .from(articleRoleEntity)
                .where(articleRoleEntity.article.eq(article))
                .fetch();
    }

    private List<ArticleStackDto> getArticleStacks(ArticleEntity article){
        return queryFactory
                .select(Projections.constructor(ArticleStackDto.class,
                        articleStackEntity.techStack.name,
                        articleStackEntity.techStack.category,
                        articleStackEntity.techStack.image))
                .from(articleStackEntity)
                .where(articleStackEntity.article.eq(article))
                .fetch();
    }

    private ArticleFormDto buildArticleFormDto(ArticleEntity article){
        return ArticleFormDto.builder()
                .articleUUID(article.getArticleUUID().toString())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updateAt(article.getUpdatedAt())
                .plannedStartAt(article.getPlannedStartAt())
                .expiredAt(article.getExpiredAt())
                .estimatedDuration(article.getEstimatedDuration())
                .viewCount(article.getViewCount())
                .bookmarkCount(article.getBookmarkCount())
                .articleType(article.getArticleType())
                .status(article.getStatus())
                .meetingType(article.getMeetingType())
                .author(article.getMember().getName())
                .articleRoleDtos(article.getArticleRoles().stream()
                        .map(role -> new ArticleRoleDto(role.getRole().getName(),
                                role.getParticipant()))
                        .toList())
                .articleStackDtos(article.getArticleStacks().stream()
                        .map(stack -> new ArticleStackDto(stack.getTechStack().getName(),
                                stack.getTechStack().getCategory(),
                                stack.getTechStack().getImage()))
                        .toList())
                .build();
    }
}
