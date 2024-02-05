package com.coconet.chatservice.controller;

import com.coconet.chatservice.common.response.Response;
import com.coconet.chatservice.dto.ChatLoadResponseDto;
import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.dto.ChatroomCreateRequestDto;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import com.coconet.chatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat-service/api/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMsgService chatMsgService;

    // for testing
    @GetMapping("/create-room")
    public String getCreateRoomForm(Model model) {
        return "/room-create";
    }
    // for testing
    @PostMapping("/create/test")
    public String createRoomTest(Model theModel, @RequestBody ChatroomCreateRequestDto chatroomCreateRequestDto,
                             @RequestHeader(value = "memberUUID") UUID memberUUID){
        ChatroomResponseDto room = chatRoomService.createRoom(chatroomCreateRequestDto, memberUUID);
        theModel.addAttribute("room", room);
        return "/room";
    }

    @PostMapping("/create")
    public Response<ChatroomResponseDto> createRoom(@RequestBody ChatroomCreateRequestDto chatroomCreateRequestDto,
                                                    @RequestHeader(value = "memberUUID") UUID memberUUID){
        ChatroomResponseDto response = chatRoomService.createRoom(chatroomCreateRequestDto, memberUUID);
        return Response.OK(response);
    }

    //for testing
    @GetMapping("/my-room/{memberUUID}/test")
    public String getMyRoomsTest(Model theModel, @PathVariable UUID memberUUID) {
        memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
        List<ChatroomResponseDto> myRooms = chatRoomService.getRooms(memberUUID);

        theModel.addAttribute("myRooms", myRooms);
        return "/room-list";
    }

    @GetMapping("/my-room")
    public Response<List<ChatroomResponseDto>> getRooms(@RequestHeader(value = "memberUUID") UUID memberUUID) {
        List<ChatroomResponseDto> response = chatRoomService.getRooms(memberUUID);
        return Response.OK(response);
    }


    //For testing
    @GetMapping("/{roomUUID}/test")
    public String getRoomTest(Model theModel, @PathVariable UUID roomUUID) {
        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");
        ChatroomResponseDto room = chatRoomService.getRoom(memberUUID, roomUUID);
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);

        theModel.addAttribute("room", room);
        theModel.addAttribute("sender", "9dfb4b63-d489-4653-aeee-11846b07906d");
        theModel.addAttribute("chats", chats);
        return "room-detail";
    }

    @GetMapping("/{roomUUID}")
    public Response<ChatLoadResponseDto> getRoom(@RequestHeader(value = "memberUUID") UUID memberUUID, UUID roomUUID) {

        ChatroomResponseDto chatroom = chatRoomService.getRoom(memberUUID, roomUUID);
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);
        ChatLoadResponseDto response = new ChatLoadResponseDto(chats, chatroom);
        return Response.OK(response);
    }
}