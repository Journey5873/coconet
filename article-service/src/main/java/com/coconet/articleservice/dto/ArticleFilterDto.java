package com.coconet.articleservice.dto;

import com.coconet.articleservice.entity.enums.MeetingType;
import com.coconet.articleservice.entity.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ArticleFilterDto {
    private String keyword;
    private ArticleType articleType;
    private MeetingType meetingType;
    private boolean bookmark;
    private List<String> roles = new ArrayList<>();
    private List<String> stacks = new ArrayList<>();
}
