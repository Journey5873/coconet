package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, ArticleRepositoryCustom {
}
