package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, MemberRepositoryCustom {

    Optional<MemberEntity> findByMemberUUID(UUID uuid);

    List<MemberEntity> findAllByEmail(String email);
}
