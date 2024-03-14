package com.coconet.articleservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "application")
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ApplicationEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "application_uuid",
            nullable = false, unique = true)
    private UUID applicationUUID;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid")
    private UUID applicantUUID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @Column(name = "application_position")
    private String applicationPosition;
}