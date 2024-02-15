package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.ArticleCreateRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import com.coconet.articleservice.dto.member.MemberResponse;
import lombok.Builder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public class ArticleConverter {

    public static ArticleResponseDto convertToDto (ArticleEntity articleEntity, MemberResponse memberResponse) {

        List<ArticleRoleDto> roles = new ArrayList<>();
        if (articleEntity.getArticleRoles() != null && articleEntity.getArticleType().equals(ArticleType.PROJECT.toString())){
            roles = articleEntity.getArticleRoles().stream()
                    .map(role -> new ArticleRoleDto(role.getRole().getName(), role.getParticipant()))
                    .toList();
        }

        List<String> stacks = new ArrayList<>();
        if (articleEntity.getArticleStacks() != null && !articleEntity.getArticleStacks().isEmpty()){
            stacks = articleEntity.getArticleStacks().stream()
                    .map(stack -> stack.getTechStack().getName())
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
                .writerName(memberResponse.getName())
                .writerProfileImage(memberResponse.getProfileImage())
                .roles(roles)
                .stacks(stacks)
                .build();
    }

    public static ArticleResponseDto convertToDto (ArticleEntity articleEntity,
                                                   boolean isBookmarked,
                                                   MemberResponse memberResponse,
                                                   List<CommentResponseDto> comments) {

        List<ArticleRoleDto> roles = new ArrayList<>();
        if (articleEntity.getArticleType().equals(ArticleType.PROJECT.toString())){
            roles = articleEntity.getArticleRoles().stream()
                    .map(role -> new ArticleRoleDto(role.getRole().getName(), role.getParticipant()))
                    .toList();
        }

        List<String> stacks = new ArrayList<>();
        if (articleEntity.getArticleStacks() != null && !articleEntity.getArticleStacks().isEmpty()){
            stacks = articleEntity.getArticleStacks().stream()
                .map(stack -> stack.getTechStack().getName())
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
                .bookmarked(isBookmarked)
                .roles(roles)
                .stacks(stacks)
                .writerName(memberResponse.getName())
                .writerProfileImage(memberResponse.getProfileImage())
                .comments(comments)
                .build();
    }

    public static ArticleEntity converterToEntity (ArticleCreateRequestDto request, UUID memberUUID) {
        return ArticleEntity.builder()
                .title(request.getTitle())
                .articleUUID(UUID.randomUUID())
                .memberUUID(memberUUID)
                .content(request.getContent())
                .plannedStartAt(request.getPlannedStartAt().atTime(LocalTime.MAX))
                .expiredAt(request.getExpiredAt().atTime(LocalTime.MAX))
                .estimatedDuration(request.getEstimatedDuration().name())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(request.getArticleType().name())
                .status((byte) 1)
                .meetingType(request.getMeetingType().name())
                .build();
    }
}