package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    List<BookmarkEntity> findAllByMemberUUID(UUID memberUUID);
    Optional<BookmarkEntity> findByArticleIdAndMemberUUID(Long articleId, UUID memberUUID);
}
