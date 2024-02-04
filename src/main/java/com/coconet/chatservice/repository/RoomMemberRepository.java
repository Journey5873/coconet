package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.RooMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomMemberRepository extends JpaRepository<RooMemberEntity, Long>, RoomMemberRepositoryCustom {
    List<RooMemberEntity> findAllByMemberUUID(UUID memberUUID);
}
