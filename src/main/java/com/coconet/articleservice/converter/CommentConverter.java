package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.CommentRequestDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.CommentEntity;
import com.coconet.articleservice.dto.member.MemberResponse;
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

    public static CommentResponseDto convertToDto(CommentEntity commentEntity, MemberResponse memberResponse) {

        return CommentResponseDto.builder()
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .memberUUID(commentEntity.getMemberUUID())
                .commentUUID(commentEntity.getCommentUUID())
                .writerName(memberResponse.getName())
                .writerProfileImage(memberResponse.getProfileImage())
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
