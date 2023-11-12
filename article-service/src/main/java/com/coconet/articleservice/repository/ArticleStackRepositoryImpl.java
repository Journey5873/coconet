package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleStackEntity;
import com.coconet.articleservice.entity.TechStackEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.coconet.articleservice.entity.QArticleStackEntity.articleStackEntity;
import static com.coconet.articleservice.entity.QTechStackEntity.techStackEntity;

@RequiredArgsConstructor
public class ArticleStackRepositoryImpl implements ArticleStackRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<ArticleStackEntity> getArticleStacks(ArticleEntity article) {
        return queryFactory.select(articleStackEntity)
                .from(articleStackEntity)
                .join(articleStackEntity.techStack, techStackEntity)
                .fetchJoin()
                .where(articleStackEntity.article.eq(article))
                .fetch();
    }

    @Override
    public List<TechStackEntity> getArticleStacksNameIn(List<String> stacksNames) {
        return queryFactory.select(techStackEntity)
                .from(techStackEntity)
                .where(techStackEntity.name.in(stacksNames))
                .fetch();
    }
}
