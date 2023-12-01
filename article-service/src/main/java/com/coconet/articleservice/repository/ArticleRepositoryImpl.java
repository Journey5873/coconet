package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleFormDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.dto.ArticleStackDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.entity.*;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
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
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public ArticleFormDto getArticle(UUID articleUUID) {

        queryFactory.update(articleEntity)
                .set(articleEntity.viewCount, articleEntity.viewCount.add(1))
                .where(articleEntity.articleUUID.eq(articleUUID))
                .execute();

        ArticleEntity article = queryFactory
                .selectFrom(articleEntity)
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .where(articleEntity.articleUUID.eq(articleUUID))
                .fetchOne();

        if (article != null) {
            return entityToFormDto(article);
        }
        return null;
    }

    @Override
    public Page<ArticleFormDto> getArticles(String keyword, ArticleType articleType, Pageable pageable) {
        List<ArticleEntity> articles = queryFactory
                .selectFrom(articleEntity)
                .distinct()
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .orderBy(articleEntity.createdAt.desc())
                .where(
                        articleEntity.status.eq((byte)1)
                                .and(articleEntity.expiredAt.after(LocalDateTime.now())),
                        containsKeyword(keyword),
                        articleTypeEquals(articleType.name())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<ArticleFormDto> contents = articles.stream()
                .map(this::entityToFormDto)
                .toList();

        JPAQuery<ArticleEntity> countQuery = queryFactory
                .selectFrom(articleEntity)
                .where(
                        articleEntity.status.eq((byte)1)
                                .and(articleEntity.expiredAt.after(LocalDateTime.now())),
                        containsKeyword(keyword),
                        articleTypeEquals(articleType.name())
                );

        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetchCount());
    }

    @Override
    public List<ArticleFormDto> getSuggestions(List<RoleEntity> memberRoles, List<TechStackEntity> memberStacks) {

        // A condition for filtering articles based on member's roles.
        BooleanExpression roleCondition = JPAExpressions.selectOne()
                .from(articleRoleEntity)
                .where(articleRoleEntity.article.eq(articleEntity))
                .groupBy(articleEntity)
                .having(articleRoleEntity.role.in(memberRoles).countDistinct().goe(1))
                .exists();
        // A condition for filtering articles based on member's tacks.
        BooleanExpression stackCondition = JPAExpressions.selectOne()
                .from(articleStackEntity)
                .where(articleStackEntity.article.eq(articleEntity))
                .groupBy(articleEntity)
                .having(articleStackEntity.techStack.in(memberStacks).countDistinct().goe(2))
                .exists();

        List<ArticleEntity> suggestions = queryFactory.selectFrom(articleEntity)
                .leftJoin(articleEntity.articleRoles, articleRoleEntity)
                .leftJoin(articleEntity.articleStacks, articleStackEntity)
                .where(
                        articleEntity.status.eq((byte) 1)
                                .and(articleEntity.expiredAt.after(LocalDateTime.now()))
                                .and(roleCondition.or(stackCondition))
                )
                .orderBy(articleEntity.createdAt.desc())
                .distinct()
                .fetch();

        return suggestions.stream()
                .map(this::entityToFormDto)
                .toList();
    }

    @Override
    public List<ArticleFormDto> getPopularPosts() {
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
                .map(this::entityToFormDto)
                .toList();
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

    private ArticleFormDto entityToFormDto(ArticleEntity article){
        return ArticleFormDto.builder()
                .articleUUID(article.getArticleUUID())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updateAt(article.getUpdatedAt())
                .plannedStartAt(article.getPlannedStartAt())
                .expiredAt(article.getExpiredAt())
                .estimatedDuration(EstimatedDuration.valueOf(article.getEstimatedDuration()))
                .viewCount(article.getViewCount())
                .bookmarkCount(article.getBookmarkCount())
                .articleType(ArticleType.valueOf(article.getArticleType()))
                .status(article.getStatus())
                .meetingType(MeetingType.valueOf(article.getMeetingType()))
                .memberUUID(article.getMemberUUID())
                .articleRoleDtos(article.getArticleRoles().stream()
                        .map(role -> new ArticleRoleDto(role.getRole().getName(),
                                role.getParticipant()))
                        .toList())
                .articleStackDtos(article.getArticleStacks().stream()
                        .map(stack -> new ArticleStackDto(stack.getTechStack().getName(),
                                stack.getTechStack().getCategory(),
                                stack.getTechStack().getImage()))
                        .toList())
                .commentResponseDtos(article.getComments().stream()
                        .map(comment -> new CommentResponseDto(comment.getCommentId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getUpdatedAt(),
                                comment.getMemberUUID()))
                        .toList())
                .build();
    }
}

