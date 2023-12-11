package com.coconet.memberservice.repository;

import com.coconet.memberservice.dto.MemberIdDto;
import com.coconet.memberservice.dto.MemberInfoDto;
import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.security.token.dto.TokenResponse;

import java.util.UUID;

public interface MemberRepositoryCustom {
    MemberInfoDto getUserInfo(UUID memberUUID);
}
