package com.coconet.memberservice.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(NON_NULL)
public class MemberClientDto {
    private String email;
    private String name;
    private UUID memberUUID;
    private byte[] profileImage;
    private List<MemberRoleResponse> roles;
    private List<MemberStackResponse> stacks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @QueryProjection
    public MemberClientDto(String name, UUID memberUUID, String profileImage) {
        this.name = name;
        this.memberUUID = memberUUID;
        this.profileImage = profileImage.getBytes();
    }
}
