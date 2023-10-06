package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.MemberStackEntity;
import com.coconet.memberservice.entity.TechStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechStackRepository extends JpaRepository<TechStackEntity, Long> {
}
