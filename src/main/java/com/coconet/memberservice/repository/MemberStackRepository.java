package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberStackRepository extends JpaRepository<MemberStackEntity, Long> {
    Optional<MemberStackEntity> findByMemberIdAndTechStackId(Long memberId, Long stackId);
    void deleteByMemberIdAndTechStackIdIn(Long memberId, List<Long> stackIds);
    List<MemberStackEntity> findAllByMember(MemberEntity member);
}
