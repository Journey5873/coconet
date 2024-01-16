package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleRoleEntity;

import java.util.List;

public interface ArticleRoleRepositoryCustom {
    List<ArticleRoleEntity> getArticleRoles(ArticleEntity article);

    Long deleteArticleRolesIn(List<Long> rolesId);
}
