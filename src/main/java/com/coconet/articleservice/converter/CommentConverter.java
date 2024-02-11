package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.CommentRequestDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.CommentEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public class CommentConverter {

    public static CommentResponseDto convertToDto(CommentEntity commentEntity) {
        return CommentResponseDto.builder()
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .memberUUID(commentEntity.getMemberUUID())
                .commentUUID(commentEntity.getCommentUUID())
                .build();
    }

    public static CommentEntity convertToEntity(CommentRequestDto request, ArticleEntity article, UUID memberUUID) {
        return CommentEntity.builder()
                .content(request.getContent())
                .memberUUID(memberUUID)
                .commentUUID(UUID.randomUUID())
                .article(article)
                .build();
    }

}
