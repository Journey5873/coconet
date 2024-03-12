package com.coconet.articleservice.converter;

import com.coconet.articleservice.dto.ArticleCreateRequestDto;
import com.coconet.articleservice.dto.ArticleResponseDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.dto.CommentResponseDto;
import com.coconet.articleservice.dto.member.MemberResponse;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import lombok.Builder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public class ArticleConverter {

    public static ArticleResponseDto convertToDto (ArticleEntity articleEntity, MemberResponse memberResponse) {

        List<ArticleRoleDto> roles = new ArrayList<>();
        if (articleEntity.getArticleRoles() != null && ArticleType.PROJECT.equals(articleEntity.getArticleType())){
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
                .estimatedDuration(articleEntity.getEstimatedDuration())
                .viewCount(articleEntity.getViewCount())
                .bookmarkCount(articleEntity.getBookmarkCount())
                .articleType(articleEntity.getArticleType())
                .status(articleEntity.getStatus())
                .meetingType(articleEntity.getMeetingType())
                .memberUUID(articleEntity.getMemberUUID())
                .writerName(memberResponse.getName())
                .writerProfileImage(memberResponse.getProfileImage())
                .roles(roles)
                .stacks(stacks)
                .build();
    }

    public static ArticleResponseDto convertToDto (ArticleEntity articleEntity,
                                                   MemberResponse memberResponse,
                                                   List<ArticleRoleDto> roles,
                                                   List<String> stacks) {

        return ArticleResponseDto.builder()
                .articleUUID(articleEntity.getArticleUUID())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .createdAt(articleEntity.getCreatedAt())
                .updateAt(articleEntity.getUpdatedAt())
                .plannedStartAt(articleEntity.getPlannedStartAt())
                .expiredAt(articleEntity.getExpiredAt())
                .estimatedDuration(articleEntity.getEstimatedDuration())
                .viewCount(articleEntity.getViewCount())
                .bookmarkCount(articleEntity.getBookmarkCount())
                .articleType(articleEntity.getArticleType())
                .status(articleEntity.getStatus())
                .meetingType(articleEntity.getMeetingType())
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
        if (ArticleType.PROJECT.equals(articleEntity.getArticleType())){
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
                .estimatedDuration(articleEntity.getEstimatedDuration())
                .viewCount(articleEntity.getViewCount())
                .bookmarkCount(articleEntity.getBookmarkCount())
                .articleType(articleEntity.getArticleType())
                .status(articleEntity.getStatus())
                .meetingType(articleEntity.getMeetingType())
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
                .plannedStartAt(request.getPlannedStartAt().atTime(LocalTime.of(23, 59, 59, 59)))
                .expiredAt(request.getExpiredAt().atTime(LocalTime.of(23, 59, 59, 59)))
                .estimatedDuration(request.getEstimatedDuration())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(request.getArticleType())
                .status((byte) 1)
                .meetingType(request.getMeetingType())
                .build();
    }
}