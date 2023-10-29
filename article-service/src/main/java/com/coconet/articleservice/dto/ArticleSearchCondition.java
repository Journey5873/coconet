package com.coconet.articleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleSearchCondition {
    private String title;
    private String content;
}
