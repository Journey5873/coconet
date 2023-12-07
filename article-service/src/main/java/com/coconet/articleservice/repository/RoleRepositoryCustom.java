package com.coconet.articleservice.repository;

import com.coconet.articleservice.entity.RoleEntity;

import java.util.List;

public interface RoleRepositoryCustom {
    List<RoleEntity> getRoles(List<String> roles);
}