package com.coconet.articleservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

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

    @OneToMany(mappedBy = "article",  cascade = CascadeType.ALL)
    private List<ArticleRoleEntity> articleRoles = new ArrayList<>();

    @OneToMany(mappedBy = "article",  cascade = CascadeType.ALL)
    private List<ArticleStackEntity> articleStacks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    public void changeTitle(String name) { this.title = title; }

    public void changeContent(String content) { this.content = content; }

    public void changeExpiredAt(LocalDateTime expiredAt) { this.expiredAt = expiredAt; }

    public void changeEstDuration(String estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public void changeArticleType(String articleType) { this.articleType = articleType; }

    public void changeStatus(Byte status) { this.status = status; }

    public void changeMeetingType(String meetingType) { this.meetingType = meetingType; }
}
