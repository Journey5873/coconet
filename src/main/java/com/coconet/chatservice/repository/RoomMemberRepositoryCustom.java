package com.coconet.chatservice.repository;

import com.coconet.chatservice.entity.RooMemberEntity;

import java.util.List;
import java.util.UUID;

public interface RoomMemberRepositoryCustom {

    List<RooMemberEntity> getMyRooms(UUID memberUUID);
}
