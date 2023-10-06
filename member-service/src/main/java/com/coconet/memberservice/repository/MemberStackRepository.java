package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberStackRepository extends JpaRepository<MemberStackEntity, Long> {

    void deleteByMember(MemberEntity member);
    List<MemberStackEntity> getAllByMember(MemberEntity member);
}
