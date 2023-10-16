package com.coconet.articleservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class ArticleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int bookmarkCount;

    @Column(nullable = false)
    private String articleType;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String meetingType;

    // 어떻게 처리해야할지 모르겠어서 코멘트 처리 해둡니다.
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private MemberEntity memberId;
}