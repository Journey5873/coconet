package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberStackEntity;

import java.util.List;

public interface MemberStackRepositoryCustom {
    List<MemberStackEntity> getAllStacks(MemberEntity member);
}
