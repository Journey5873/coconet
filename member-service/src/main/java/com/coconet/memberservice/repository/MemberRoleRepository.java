package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRoleEntity, Long>, MemberRoleRepositoryCustom {

    List<MemberRoleEntity> findByMemberId(Long memberId);

    Optional<MemberRoleEntity> findByMemberIdAndRoleId(Long memberId, Long roleId);

    List<MemberRoleEntity> findAllByMemberId(Long memberId);

    void deleteByMemberIdAndRoleIdIn(Long memberId, List<Long> rolesId);
}
