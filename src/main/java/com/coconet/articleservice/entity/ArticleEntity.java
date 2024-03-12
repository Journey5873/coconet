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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstimatedDuration estimatedDuration;

    private int viewCount;

    private int bookmarkCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleType articleType;

    private byte status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeetingType meetingType;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleRoleEntity> articleRoles = new ArrayList<>();

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleStackEntity> articleStacks = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<BookmarkEntity> bookmarks;

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> comments;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid",
            nullable = false, unique = true)
    private UUID memberUUID;

    public void updateArticle(String title, String content, LocalDateTime plannedStartAt, LocalDateTime expiredAt,
                       EstimatedDuration estimatedDuration, ArticleType articleType, MeetingType meetingType) {
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

