package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.dto.ArticleStackDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleRoleEntity;
import com.coconet.articleservice.entity.ArticleStackEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
public class ArticleEntityConverter {
    public static ArticleResponseDto convertToDto (ArticleEntity articleEntity) {

        List<ArticleRoleDto> roles = new ArrayList<>();
        if (articleEntity.getArticleType().equals(ArticleType.PROJECT.toString())){
            roles = articleEntity.getArticleRoles().stream()
                    .map(role -> new ArticleRoleDto(role.getRole().getName(), role.getParticipant()))
                    .toList();
        }

        List<String> stacks = new ArrayList<>();
        if (!articleEntity.getArticleStacks().isEmpty()){
            stacks = articleEntity.getArticleStacks().stream()
                .map(stack -> stack.getTechStack().getName())
                .toList();
        }

        List<CommentResponseDto> comments = new ArrayList<>();
        if (!articleEntity.getComments().isEmpty()){
            comments = articleEntity.getComments().stream()
                .map(comment -> new CommentResponseDto(comment.getCommentId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        comment.getMemberUUID()))
                .toList();
        }

        return ArticleResponseDto.builder()
                .articleUUID(articleEntity.getArticleUUID())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .createdAt(articleEntity.getCreatedAt())
                .updateAt(articleEntity.getUpdatedAt())
                .plannedStartAt(articleEntity.getPlannedStartAt())
                .expiredAt(articleEntity.getExpiredAt())
                .estimatedDuration(EstimatedDuration.valueOf(articleEntity.getEstimatedDuration()))
                .viewCount(articleEntity.getViewCount())
                .bookmarkCount(articleEntity.getBookmarkCount())
                .articleType(ArticleType.valueOf(articleEntity.getArticleType()))
                .status(articleEntity.getStatus())
                .meetingType(MeetingType.valueOf(articleEntity.getMeetingType()))
                .memberUUID(articleEntity.getMemberUUID())
                .roles(roles)
                .stacks(stacks)
                .comments(comments)
                .build();
    }
}