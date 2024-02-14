package com.coconet.chatservice.service;

import com.coconet.chatservice.client.ArticleClient;
import com.coconet.chatservice.common.errorcode.ErrorCode;
import com.coconet.chatservice.common.exception.ApiException;
import com.coconet.chatservice.converter.ChatRoomEntityConverter;
import com.coconet.chatservice.dto.ChatRoomDeleteDto;
import com.coconet.chatservice.dto.ChatroomRequestDto;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.dto.client.ArticleResponse;
import com.coconet.chatservice.entity.ChatRoomEntity;
import com.coconet.chatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ArticleClient articleClient;
    private final ChatRoomSubService chatRoomSubService;

    // TODO : the applicant UUID cannot be the same as writerUUID
    public ChatroomResponseDto createRoom(ChatroomRequestDto createRequestDto, UUID memberUUID){
        if(chatRoomSubService.existChatRoom(createRequestDto.getArticleUUID(), memberUUID)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Already exist");
        }

        ArticleResponse article = articleClient.sendChatClient(createRequestDto.getArticleUUID()).getData();

        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomUUID(UUID.randomUUID())
                .articleUUID(createRequestDto.getArticleUUID())
                .applicantUUID(memberUUID)
                .writerUUID(article.getWriterUUID())
                .roomName(chatRoomSubService.punctuateTitle(article.getRoomName()))
                .build();

        ChatRoomEntity newChatRoom = chatRoomRepository.save(chatRoom);

        return ChatRoomEntityConverter.convertToDto(newChatRoom);
    }

    // Todo: Paging
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

    // websocket => .. 어떻게 끊죠 ??
    // 좀더 찾아보기로.
    public ChatroomResponseDto leaveRoom(UUID memberUUID, ChatRoomDeleteDto chatRoomDeleteDto) {
        if (!chatRoomRepository.isMember(memberUUID, chatRoomDeleteDto.getRoomUUID()))
            throw new ApiException(ErrorCode.BAD_REQUEST, "Not Authorised");

        ChatRoomEntity roomEntity = chatRoomRepository
                .findByRoomUUID(chatRoomDeleteDto.getRoomUUID());
        roomEntity.leave(memberUUID);
        ChatRoomEntity response = chatRoomRepository.save(roomEntity);
        return ChatRoomEntityConverter.convertToDto(response);
    }
}
