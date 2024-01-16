package com.coconet.articleservice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
public class ArticleStackDto {
    private String stackName;
    private String category;
    private String image;

    @QueryProjection
    public ArticleStackDto(String name, String category, String image) {
        this.stackName = name;
        this.category = category;
        this.image = image;
    }
}
