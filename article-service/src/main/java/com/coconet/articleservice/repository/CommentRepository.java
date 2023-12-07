package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.CommentEntity;
import com.coconet.articleservice.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
