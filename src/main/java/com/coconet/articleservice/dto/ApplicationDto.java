package com.coconet.articleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ApplicationDto {
    private UUID articleUUID;
    private UUID writerUUID;
    private String articleName;
    private LocalDateTime applicationDate;
    private String applicationPosition;
}
