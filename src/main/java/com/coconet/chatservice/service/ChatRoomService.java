package com.coconet.chatservice.service;

import com.coconet.chatservice.converter.ChatRoomEntityConverter;
import com.coconet.chatservice.dto.RoomCreateRequestDto;
import com.coconet.chatservice.dto.RoomResponseDto;
import com.coconet.chatservice.entity.ChatRoomEntity;
import com.coconet.chatservice.entity.RooMemberEntity;
import com.coconet.chatservice.repository.ChatRoomRepository;
import com.coconet.chatservice.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;
    public RoomResponseDto createRoom(RoomCreateRequestDto createRequestDto, @RequestParam UUID memberUUID){
        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomUUID(UUID.randomUUID())
                .articleUUID(createRequestDto.getArticleUUID())
                .applicantUUID(memberUUID)
                .roomName(createRequestDto.getRoomName())
                .build();

        ChatRoomEntity newChatRoom = chatRoomRepository.save(chatRoom);

        createRoomMember(newChatRoom, memberUUID); // applicant
        createRoomMember(newChatRoom, createRequestDto.getWriterUUID()); // writer

        return ChatRoomEntityConverter.convertToDto(newChatRoom);
    }


    public RooMemberEntity createRoomMember(ChatRoomEntity chatRoomEntity, UUID memberUUID){
        RooMemberEntity rooMemberEntity = RooMemberEntity.builder()
                .chatRoom(chatRoomEntity)
                .memberUUID(memberUUID)
                .build();
        return roomMemberRepository.save(rooMemberEntity);
    }

    public List<RoomResponseDto> getMyRooms(UUID memberUUID) {
        List<RooMemberEntity> rooMemberEntities = roomMemberRepository.getMyRooms(memberUUID);

        return rooMemberEntities.stream()
                .map(rooMemberEntity -> {
                    ChatRoomEntity chatRoom = rooMemberEntity.getChatRoom();
                    List<UUID> memberUUIDs = chatRoom.getRoomMembers().stream()
                            .map(RooMemberEntity::getMemberUUID)
                            .toList();

                    return ChatRoomEntityConverter.convertToDtoWithMembers(chatRoom, memberUUIDs);
                })
                .toList();
    }

    public RoomResponseDto getRoom(UUID roomUUID){
        ChatRoomEntity roomEntity = chatRoomRepository.findByRoomUUID(roomUUID);
        return ChatRoomEntityConverter.convertToDto(roomEntity);
    }
}
