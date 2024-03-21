package com.coconet.chatservice.controller;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.*;
import com.coconet.chatservice.service.ChatMsgService;
import com.coconet.chatservice.service.ChatRoomService;
import com.coconet.chatservice.service.ChatRoomSubService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-service/api/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMsgService chatMsgService;
    private final ChatRoomSubService chatRoomSubService;

    @PostMapping("/apply")
    public Response<ChatroomResponseDto> applyJob(@RequestBody ChatroomRequestDto chatroomRequestDto,
                                                  @RequestHeader(value = "memberUUID") UUID memberUUID){

        if(chatRoomSubService.existChatRoom(chatroomRequestDto.getArticleUUID(), memberUUID)) {
          // Load room details
            ChatroomResponseDto response = chatRoomService.getRoomWithArticleUUID(memberUUID, chatroomRequestDto.getArticleUUID());
            return Response.OK(response);
        }
        // Create a room
        ChatroomResponseDto response = chatRoomService.createRoom(chatroomRequestDto, memberUUID);

        return Response.OK(response);
    }

    // Todo : paging
    @GetMapping("/my-room")
    public Response<Page<ChatroomResponseDto>> getListOfRooms(@RequestHeader(value = "memberUUID") UUID memberUUID, Pageable pageable) {
        Page<ChatroomResponseDto> response = chatRoomService.getRooms(memberUUID, pageable);
        return Response.OK(response);
    }

    @GetMapping("/{roomUUID}")
    public Response<ChatLoadResponseDto> getRoom(@PathVariable UUID roomUUID,
                                                 @RequestHeader(value = "memberUUID") UUID memberUUID) {

        ChatroomResponseDto chatroom = chatRoomService.getRoomWithRoomUUID(memberUUID, roomUUID);
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);
        ChatLoadResponseDto response = new ChatLoadResponseDto(chats, chatroom);
        return Response.OK(response);
    }

    // N/A
    @DeleteMapping("/leave")
    public Response<ChatroomResponseDto> leaveRoom(@RequestHeader(value = "memberUUID") UUID memberUUID,
                                                   @RequestBody ChatRoomDeleteDto chatRoomDeleteDto) {
        ChatroomResponseDto response = chatRoomService.leaveRoom(memberUUID, chatRoomDeleteDto);
        return Response.OK(response);
    }
}