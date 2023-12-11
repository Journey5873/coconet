package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberRoleEntity;
import com.coconet.memberservice.entity.RoleEntity;

import java.util.List;

public interface MemberRoleRepositoryCustom {
    List<MemberRoleEntity> getAllRoles(MemberEntity member);
}
