package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, Long> {

    List<MemberRoleEntity> findByMemberId(Long memberId);

    Optional<MemberRoleEntity> findByMemberIdAndRoleId(Long memberId, Long roleId);

    List<MemberRoleEntity> findAllByMemberId(Long memberId);
}
