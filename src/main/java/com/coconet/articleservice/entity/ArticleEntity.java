package com.coconet.articleservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
    private List<BookmarkEntity> bookmarks;

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> comments;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid",
            nullable = false, unique = true)
    private UUID memberUUID;

    public void updateArticle(String title, String content, LocalDateTime plannedStartAt, LocalDateTime expiredAt,
                       String estimatedDuration, String articleType, String meetingType) {
        this.title = title;
        this.content = content;
        this.plannedStartAt = plannedStartAt;
        this.expiredAt = expiredAt;
        this.estimatedDuration = estimatedDuration;
        this.articleType = articleType;
        this.meetingType = meetingType;
    }

    public void changeStatus(Byte status) { this.status = status; }

    public void setBookmark() {
        this.bookmarkCount++;
    }

    public void deleteBookmark() {
        this.bookmarkCount--;
    }
}

