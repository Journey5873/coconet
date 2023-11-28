package com.coconet.articleservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Table(name = "reply")
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(columnDefinition = "TEXT(500)", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime repliedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "member_UUID")
    private UUID memberUUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article;

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
