package com.coconet.articleservice.entity;


import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "article")
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ArticleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "article_uuid",
            nullable = false, unique = true)
    private UUID articleUUID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime plannedStartAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private String estimatedDuration;

    private int viewCount;

    private int bookmarkCount;

    @Column(nullable = false)
    private String articleType;

    private byte status;

    @Column(nullable = false)
    private String meetingType;

    @OneToMany(mappedBy = "article")
    private List<ArticleRoleEntity> articleRoles;

    @OneToMany(mappedBy = "article")
    private List<ArticleStackEntity> articleStacks;

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> replies;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid",
            nullable = false, unique = true)
    private UUID memberUUID;

    public void changeTitle(String title) { this.title = title; }
    public void changeContent(String content) { this.content = content; }
    public void changPlannedStartAt(LocalDateTime plannedStartAt) {this.plannedStartAt = plannedStartAt; }
    public void changeExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }
    public void changeEstDuration(String estimatedDuration) { this.estimatedDuration = estimatedDuration; }
    public void changeArticleType(String articleType) { this.articleType = articleType; }
    public void changeStatus(Byte status) { this.status = status; }
    public void changeMeetingType(String meetingType) { this.meetingType = meetingType; }

    public void setBookmark() {
        this.bookmarkCount++;
    }

    public void deleteBookmark() {
        this.bookmarkCount--;
    }
}

