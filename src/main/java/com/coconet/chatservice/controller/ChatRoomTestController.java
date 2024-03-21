package com.coconet.chatservice.controller;

import com.coconet.chatservice.dto.ChatMsgResponseDto;
import com.coconet.chatservice.dto.ChatroomRequestDto;
import com.coconet.chatservice.dto.ChatroomResponseDto;
import com.coconet.chatservice.service.ChatMsgService;
import com.coconet.chatservice.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat-service/api/room")
public class ChatRoomTestController {
    private final ChatRoomService chatRoomService;
    private final ChatMsgService chatMsgService;

    // for testing
    @GetMapping("/create-room")
    public String getCreateRoomForm(Model model) {
        return "/room-create";
    }
    // for testing
    @PostMapping("/create/test")
    public String createRoomTest(Model theModel, @RequestBody ChatroomRequestDto chatroomRequestDto){
        ChatroomResponseDto room = chatRoomService.createRoom(chatroomRequestDto, UUID.fromString("31323361-7364-0000-0000-000000000000"));
        theModel.addAttribute("room", room);
        return "/room";
    }

    //for testing
    @GetMapping("/my-room/{memberUUID}/test")
    public String getMyRoomsTest(Model theModel, @PathVariable UUID memberUUID, Pageable pageable) {
        memberUUID = UUID.fromString("9dfb4b63-d489-4653-aeee-11846b07906d");
        Page<ChatroomResponseDto> myRooms = chatRoomService.getRooms(memberUUID, pageable);

        theModel.addAttribute("myRooms", myRooms);
        return "/room-list";
    }

    @GetMapping("/{roomUUID}/test")
    public String getRoomTest(Model theModel, @PathVariable UUID roomUUID) {
        UUID memberUUID = UUID.fromString("31323361-7364-0000-0000-000000000000");
        ChatroomResponseDto room = chatRoomService.getRoomWithRoomUUID(memberUUID, roomUUID);
        List<ChatMsgResponseDto> chats = chatMsgService.loadChats(roomUUID);

        theModel.addAttribute("room", room);
        theModel.addAttribute("sender", "9dfb4b63-d489-4653-aeee-11846b07906d");
        theModel.addAttribute("chats", chats);
        return "room-detail";
    }
}