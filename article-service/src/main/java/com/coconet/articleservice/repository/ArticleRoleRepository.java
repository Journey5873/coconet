package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.ArticleRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRoleRepository extends JpaRepository<ArticleRoleEntity, Long> {
}
