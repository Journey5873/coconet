package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleRoleEntity;
import com.coconet.articleservice.entity.ArticleStackEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private UUID articleUUID;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private LocalDateTime plannedStartAt;
    private LocalDateTime expiredAt;
    private EstimatedDuration estimatedDuration;
    private int viewCount;
    private int bookmarkCount;
    private ArticleType articleType;
    private Byte status;
    private MeetingType meetingType;
    private UUID memberUUID;

    private List<ArticleRoleDto> roles = new ArrayList<>();
    private List<ArticleStackDto> stacks = new ArrayList<>();
    private List<CommentResponseDto> comments = new ArrayList<>();

    @Builder
    public ArticleResponseDto(ArticleEntity articleEntity, List<ArticleRoleEntity> roles, List<ArticleStackEntity> stacks) {
        this.articleUUID = articleEntity.getArticleUUID();
        this.title = articleEntity.getTitle();
        this.content = articleEntity.getContent();
        this.createdAt = articleEntity.getCreatedAt();
        this.updateAt = articleEntity.getUpdatedAt();
        this.expiredAt = articleEntity.getExpiredAt();
        this.estimatedDuration = EstimatedDuration.valueOf(articleEntity.getEstimatedDuration());
        this.viewCount = articleEntity.getViewCount();
        this.bookmarkCount = articleEntity.getBookmarkCount();
        this.articleType = ArticleType.valueOf(articleEntity.getArticleType());
        this.status = articleEntity.getStatus();
        this.meetingType = MeetingType.valueOf(articleEntity.getMeetingType());
        this.memberUUID = articleEntity.getMemberUUID();

        List<ArticleRoleDto> articleRoleDtoList = roles.stream()
                .map(articleRoleEntity -> {
                    return ArticleRoleDto.builder()
                            .roleName(articleRoleEntity.getRole().getName())
                            .participant(articleRoleEntity.getParticipant())
                            .build();
                }).collect(Collectors.toList());

        List<ArticleStackDto> articleStackDtoList = stacks.stream()
                .map(articleStackEntity -> {
                    return ArticleStackDto.builder()
                            .stackName(articleStackEntity.getTechStack().getName())
                            .category(articleStackEntity.getTechStack().getCategory())
                            .image(articleStackEntity.getTechStack().getImage())
                            .build();
                }).collect(Collectors.toList());

        this.roles = articleRoleDtoList;
        this.stacks = articleStackDtoList;

//        this.roles = articleEntity.getArticleRoles().stream()
//                .map(role -> new ArticleRoleDto(role.getRole().getName(),
//                        role.getParticipant()))
//                .toList();
//        this.stacks = articleEntity.getArticleStacks().stream()
//                .map(stack -> new ArticleStackDto(stack.getTechStack().getName(), stack.getTechStack().getCategory(),
//                        stack.getTechStack().getImage()))
//                .toList();
//        this.comments = articleEntity.getComments().stream()
//                .map(comment -> new CommentResponseDto(comment.getCommentId(),
//                        comment.getContent(),
//                        comment.getCreatedAt(),
//                        comment.getUpdatedAt(),
//                        comment.getMemberUUID()))
//                .toList();
    }

}