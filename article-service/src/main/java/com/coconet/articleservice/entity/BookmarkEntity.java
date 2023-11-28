package com.coconet.articleservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "bookmark")
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class BookmarkEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @Column(columnDefinition = "BINARY(16)", name = "member_UUID", nullable = false)
    private UUID memberUUID;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

}
