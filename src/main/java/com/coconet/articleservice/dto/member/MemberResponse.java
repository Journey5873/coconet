package com.coconet.articleservice.dto.member;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class MemberResponse {
    private String email;
    private String name;
    private UUID memberUUID;
    private String profileImage;
    private List<MemberRoleResponse> roles;
    private List<MemberStackResponse> stacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
