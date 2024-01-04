package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleStackEntity;
import com.coconet.articleservice.entity.TechStackEntity;

import java.util.List;

public interface ArticleStackRepositoryCustom {
    List<ArticleStackEntity> getArticleStacks(ArticleEntity article);

    List<TechStackEntity> getArticleStackNamesIn(List<String> stacksNames);
}
