package com.coconet.memberservice.repository;

import com.coconet.memberservice.dto.MemberInfoDto;
import com.coconet.memberservice.entity.MemberEntity;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepositoryCustom {
    MemberInfoDto getUserInfo(UUID memberUUID);

    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByEmail(String email);
}
