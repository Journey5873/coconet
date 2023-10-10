package com.coconet.memberservice.repository;

import com.coconet.memberservice.entity.TechStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStackEntity, Long> {
    Optional<TechStackEntity> findByName(String name);
}
