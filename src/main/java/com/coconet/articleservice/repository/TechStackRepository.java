package com.coconet.articleservice.repository;


import com.coconet.articleservice.entity.TechStackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechStackRepository extends JpaRepository<TechStackEntity, Long>, TechStackRepositoryCustom{
    Optional<TechStackEntity> findByName(String name);

    List<TechStackEntity> findByNameIn(List<String> stacks);
}
