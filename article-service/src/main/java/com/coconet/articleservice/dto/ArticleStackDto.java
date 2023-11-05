package com.coconet.articleservice.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ArticleStackDto {
    private String name;
    private String category;
    private String image;

    @QueryProjection
    public ArticleStackDto(String name, String category, String image) {
        this.name = name;
        this.category = category;
        this.image = image;
    }
}
