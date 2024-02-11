package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByCommentUUID(UUID commentUUID);
}
