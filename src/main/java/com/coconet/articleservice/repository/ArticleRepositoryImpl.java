package com.coconet.articleservice.repository;


import com.coconet.articleservice.converter.ArticleConverter;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.RoleEntity;
import com.coconet.articleservice.entity.TechStackEntity;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.coconet.articleservice.entity.QArticleEntity.articleEntity;
import static com.coconet.articleservice.entity.QArticleRoleEntity.articleRoleEntity;
import static com.coconet.articleservice.entity.QArticleStackEntity.articleStackEntity;
import static com.coconet.articleservice.entity.QBookmarkEntity.bookmarkEntity;
import static org.springframework.util.ObjectUtils.isEmpty;


@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public ArticleResponseDto getArticle(UUID articleUUID) {

        queryFactory.update(articleEntity)
                .set(articleEntity.viewCount, articleEntity.viewCount.add(1))
                .where(articleEntity.articleUUID.eq(articleUUID))
                .execute();

        ArticleEntity article = queryFactory
                .selectFrom(articleEntity)
                .where(articleEntity.articleUUID.eq(articleUUID))
                .fetchOne();

        return ArticleConverter.convertToDto(article);
    }


    @Override
    public Page<ArticleResponseDto> getArticles(List<RoleEntity> roles, List<TechStackEntity> stacks, String keyword,
                                            String articleType, String meetingType, boolean bookmark, UUID memberUUID, Pageable pageable) {

        Predicate condition = articleEntity.status.eq((byte) 1)
                .and(articleEntity.expiredAt.after(LocalDateTime.now()))
                .and(containsKeyword(keyword))
                .and(articleTypeEquals(articleType))
                .and(meetingTypeEquals(meetingType))
                .and(articleRoleContains(roles))
                .and(articleStackContains(stacks));

        JPAQuery<ArticleEntity> query = queryFactory.selectFrom(articleEntity)
                .distinct()
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .where(condition);

        query = handleBookmark(query, bookmark, memberUUID);

        List<ArticleEntity> articles = query
                .orderBy(articleEntity.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ArticleResponseDto> contents = articles.stream()
                .map(article -> ArticleConverter.convertToDto(article))
                .toList();

        JPAQuery<ArticleEntity> countQuery = queryFactory
                .selectFrom(articleEntity)
                .where(condition);

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetchCount());
    }

    private JPAQuery<ArticleEntity> handleBookmark(JPAQuery<ArticleEntity> query, boolean bookmark, UUID memberUUID) {
        if (bookmark && !memberUUID.toString().isBlank()) {
            query.leftJoin(articleEntity.bookmarks, bookmarkEntity)
                    .where(bookmarkEntity.memberUUID.eq(memberUUID));
        }
        return query;
    }

    private BooleanExpression containsKeyword(String keyword){
        return isEmpty(keyword) ? null : titleContains(keyword).or(contentContains(keyword));
    }

    private BooleanExpression titleContains(String title){
        return isEmpty(title) ? null : articleEntity.title.contains(title);
    }


    private BooleanExpression contentContains(String content){
        return isEmpty(content) ? null : articleEntity.content.contains(content);
    }

    private BooleanExpression articleTypeEquals(String type) {
        return isEmpty(type) ? null : articleEntity.articleType.eq(type);
    }

    private BooleanExpression meetingTypeEquals(String type) {
        return isEmpty(type) ? null : articleEntity.meetingType.eq(type);
    }

    private BooleanExpression articleRoleContains(List<RoleEntity> roles){
        return isEmpty(roles) ? null : articleRoleEntity.role.in(roles);
    }

    private BooleanExpression articleStackContains(List<TechStackEntity> stacks){
        return isEmpty(stacks) ? null : articleStackEntity.techStack.in(stacks);
    }


    @Override
    public Page<ArticleResponseDto> getMyArticles(UUID memberUUID, Pageable pageable) {

        List<ArticleEntity> articles = queryFactory.selectFrom(articleEntity)
                .distinct()
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .where(articleEntity.memberUUID.eq(memberUUID).and(articleEntity.status.eq((byte)1)))
                .orderBy(articleEntity.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ArticleResponseDto> contents = articles.stream()
                .map(article -> ArticleConverter.convertToDto(article))
                .toList();


        JPAQuery<ArticleEntity> countQuery = queryFactory
                .selectFrom(articleEntity);

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public List<ArticleResponseDto> getSuggestions(List<RoleEntity> memberRoles, List<TechStackEntity> memberStacks) {

        // A condition for filtering articles based on member's roles.
        BooleanExpression roleCondition = JPAExpressions.selectOne()
                .from(articleRoleEntity)
                .where(articleRoleEntity.article.eq(articleEntity)
                        .and(articleRoleEntity.role.id.in(
                                memberRoles.stream().map(RoleEntity::getId).toList())))
                .groupBy(articleRoleEntity.article)
                .having(articleRoleEntity.role.count().goe(1))
                .exists();

        // A condition for filtering articles based on member's stacks.
        BooleanExpression stackCondition = JPAExpressions.selectOne()
                .from(articleStackEntity)
                .where(articleStackEntity.article.eq(articleEntity)
                        .and(articleStackEntity.techStack.id.in(
                                memberStacks.stream().map(TechStackEntity::getId).toList())))
                .groupBy(articleStackEntity.article)
                .having(articleStackEntity.techStack.count().goe(2))
                .exists();

        List<ArticleEntity> suggestions = queryFactory.selectFrom(articleEntity)
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .where(
                        articleEntity.status.eq((byte) 1)
                                .and(articleEntity.expiredAt.after(LocalDateTime.now()))
                                .and(roleCondition.or(stackCondition))
                )
                .orderBy(articleEntity.updatedAt.desc())
                .distinct()
                .fetch();


        return suggestions.stream()
                .map(suggestion -> ArticleConverter.convertToDto(suggestion))
                .toList();
    }

    @Override
    public List<ArticleResponseDto> getPopularPosts() {
        List<ArticleEntity> populars = queryFactory.selectFrom(articleEntity)
                .where(
                        articleEntity.status.eq((byte) 1)
                                .and(articleEntity.expiredAt.after(LocalDateTime.now()))
                )
                .orderBy(
                        articleEntity.viewCount.desc(),
                        articleEntity.bookmarkCount.desc(),
                        articleEntity.createdAt.desc()
                )
                .limit(7)
                .distinct()
                .fetch();

        return populars.stream()
                .map(popularPost -> ArticleConverter.convertToDto(popularPost))
                .toList();
    }
}




