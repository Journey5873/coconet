package com.coconet.memberservice.repository;

import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.entity.MemberEntity;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepositoryCustom {
    MemberResponseDto getUserInfo(UUID memberUUID);

    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByEmail(String email);
}
