package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByName(String name);

    Optional<MemberEntity> findByMemberId(String name);
}
