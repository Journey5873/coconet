package com.coconet.chatservice.controller;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.*;
import com.coconet.chatservice.service.ChatMsgService;
import com.coconet.chatservice.service.ChatRoomService;
import com.coconet.chatservice.service.ChatRoomSubService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create")
    public Response<ChatroomResponseDto> createRoom(@RequestBody ChatroomRequestDto chatroomRequestDto
                                                    ){
//        @RequestHeader(value = "memberUUID") UUID memberUUID
        UUID memberUUID = UUID.fromString("6a2e74dc-beab-4d68-99fb-c15cc48dd455");
        ChatroomResponseDto response = chatRoomService.createRoom(chatroomRequestDto, memberUUID);
        return Response.OK(response);
    }

    @GetMapping("/my-room")
    public Response<List<ChatroomResponseDto>> getRooms(@RequestHeader(value = "memberUUID") UUID memberUUID) {
        List<ChatroomResponseDto> response = chatRoomService.getRooms(memberUUID);
        return Response.OK(response);
    }

    // Todo : paging
    @GetMapping("/{roomUUID}")
    public Response<ChatLoadResponseDto> getRoom(
                                                 @PathVariable UUID roomUUID) {
        //@RequestHeader(value = "memberUUID") UUID memberUUID,
        UUID memberUUID = UUID.fromString("6a2e74dc-beab-4d68-99fb-c15cc48dd455");
        ChatroomResponseDto chatroom = chatRoomService.getRoom(memberUUID, roomUUID);
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);
        ChatLoadResponseDto response = new ChatLoadResponseDto(chats, chatroom);
        return Response.OK(response);
    }

    @DeleteMapping("/leave")
    public Response<ChatroomResponseDto> leaveRoom(@RequestHeader(value = "memberUUID") UUID memberUUID,
                                                   @RequestBody ChatRoomDeleteDto chatRoomDeleteDto) {
        ChatroomResponseDto response = chatRoomService.leaveRoom(memberUUID, chatRoomDeleteDto);
        return Response.OK(response);
    }
}