package com.coconet.memberservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@Schema(name = "Member Token", description = "Member info to issue tokens")
public class MemberTokenDto {
    private UUID memberUUID;
    private String email;
}
