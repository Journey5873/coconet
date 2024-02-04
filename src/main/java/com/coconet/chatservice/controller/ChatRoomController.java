package com.coconet.chatservice.controller;

import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.dto.RoomCreateRequestDto;
import com.coconet.chatservice.dto.RoomResponseDto;
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

//    @PostMapping("/create")
//    public Response<RoomResponseDto> createRoom(@RequestBody RoomCreateRequestDto roomCreateRequestDto, @RequestParam UUID memberUUID){
//        memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
//        return Response.OK(chatRoomService.createRoom(roomCreateRequestDto, memberUUID));
//    }

    @GetMapping("/create-room")
    public String getCreateRoomForm(Model model) {
        return "/room-create";
    }

    @PostMapping("/create")
    public String createRoom(Model theModel, @RequestBody RoomCreateRequestDto roomCreateRequestDto){
        UUID memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
        RoomResponseDto room = chatRoomService.createRoom(roomCreateRequestDto, memberUUID);
        theModel.addAttribute("room", room);
        return "/room";
    }

//    @GetMapping("/my-room/{memberUUID}")
//    public Response<List<RoomResponseDto>> getMyRooms(@PathVariable UUID memberUUID) {
//        memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
//        return Response.OK(chatRoomService.getMyRooms(memberUUID));
//    }

    @GetMapping("/my-room/{memberUUID}")
    public String getMyRooms(Model theModel, @PathVariable UUID memberUUID) {
        memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
        List<RoomResponseDto> myRooms = chatRoomService.getMyRooms(memberUUID);

        theModel.addAttribute("myRooms", myRooms);
        return "/room-list";
    }

    @GetMapping("/{roomUUID}")
    public String getRoom(Model theModel, @PathVariable String roomUUID) {

        RoomResponseDto room = chatRoomService.getRoom(UUID.fromString(roomUUID));
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);

        theModel.addAttribute("room", room);
        theModel.addAttribute("sender", "9dfb4b63-d489-4653-aeee-11846b07906d");
        theModel.addAttribute("chats", chats);
        return "room-detail";
    }
}