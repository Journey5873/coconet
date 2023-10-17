package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.ArticleStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleStackRepository extends JpaRepository<ArticleStackEntity, Long> {
}
