package com.example.ProjectCC.controller;

import com.example.ProjectCC.DTO.ChatRoom;
import com.example.ProjectCC.repository.ChatRoomRepository;
import com.example.ProjectCC.repository.UserRepository;
import com.example.ProjectCC.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/createChat")
    public String createChat(HttpSession session, Model model) {
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));

        return "createChat";
    }

    @GetMapping("/findUser")
    public String findUser(@RequestParam(value = "name")String name, Model model, HttpSession session) {
        model.addAttribute("findUser", chatService.findAllByName(name));
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));

        return "createChat";
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam(value = "member", required = false) String member, ChatRoom chatRoom, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (member != null) {
            String members[] = member.split(" ");

            chatService.saveChatRoom(member, chatRoom);

            session.setAttribute("roomNum", chatService.roomNum(member));
            session.setAttribute("myId", members[0]);
            session.setAttribute("yourId", members[1]);
            session.setAttribute("yourName", chatService.findById(members[1]));
        }

        model.addAttribute("roomNum", session.getAttribute("roomNum"));
        model.addAttribute("myId",  session.getAttribute("myId"));
        model.addAttribute("yourId",  session.getAttribute("yourId"));
        model.addAttribute("yourName",  session.getAttribute("yourName"));
        model.addAttribute("login_user", session.getAttribute("login_user"));

        return "chatting";
    }
}
