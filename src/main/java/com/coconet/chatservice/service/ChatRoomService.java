package com.coconet.chatservice.service;

import com.coconet.chatservice.client.ArticleClient;
import com.coconet.chatservice.common.errorcode.ErrorCode;
import com.coconet.chatservice.common.exception.ApiException;
import com.coconet.chatservice.converter.ChatRoomEntityConverter;
import com.coconet.chatservice.dto.ChatroomCreateRequestDto;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.dto.client.ArticleResponse;
import com.coconet.chatservice.entity.ChatRoomEntity;
import com.coconet.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ArticleClient articleClient;
    public ChatroomResponseDto createRoom(ChatroomCreateRequestDto createRequestDto, @RequestParam UUID memberUUID){

        ArticleResponse article = articleClient.sendChatClient(createRequestDto.getArticleUUID()).getData();

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomUUID(UUID.randomUUID())
                .articleUUID(createRequestDto.getArticleUUID())
                .applicantUUID(memberUUID)
                .writerUUID(article.getWriterUUID())
                .roomName(article.getRoomName())
                .build();

        ChatRoomEntity newChatRoom = chatRoomRepository.save(chatRoom);

        return ChatRoomEntityConverter.convertToDto(newChatRoom);
    }

    public List<ChatroomResponseDto> getRooms(UUID memberUUID) {
        List<ChatRoomEntity> chatRoomEntities = chatRoomRepository.findByAllMemberUUID(memberUUID);

        return chatRoomEntities.stream()
                .map(chatRoomEntity -> ChatRoomEntityConverter.convertToDto(chatRoomEntity))
                .toList();
    }

    public ChatroomResponseDto getRoom(UUID memberUUID, UUID roomUUID){
        if (!chatRoomRepository.isMember(memberUUID, roomUUID))
            throw new ApiException(ErrorCode.BAD_REQUEST, "Not Authorised");

        ChatRoomEntity roomEntity = chatRoomRepository.findByRoomUUID(roomUUID);
        return ChatRoomEntityConverter.convertToDto(roomEntity);
    }
}
