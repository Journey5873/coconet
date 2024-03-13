package com.coconet.articleservice.repository;

import com.coconet.articleservice.dto.ApplicationDto;
import com.coconet.articleservice.entity.ApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    Page<ApplicationEntity> findAllByMemberUUID(UUID memberUUID, Pageable pageable);

}
