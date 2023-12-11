package com.coconet.memberservice.repository;

import com.coconet.memberservice.dto.MemberInfoDto;

import java.util.UUID;

public interface MemberRepositoryCustom {
    MemberInfoDto getUserInfo(UUID memberUUID);
}
