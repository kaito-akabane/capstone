package com.example.ProjectCC.controller;

import com.example.ProjectCC.domain.ChatRoom;
import com.example.ProjectCC.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/createChat")
    public String createChat(HttpSession session, Model model) {
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));
        model.addAttribute("chatRoomMsg", chatService.findChat((String) session.getAttribute("login_id")));
        model.addAttribute("userNames", chatService.findChatName((String) session.getAttribute("login_id")));

        return "createChat";
    }

    @PostMapping("/createChat/Declaration")
    @ResponseBody
    public void createChat(@RequestBody Map<String, String> map) {
        chatService.saveDecla(map);
    }

    @GetMapping("/findUser")
    public String findUser(@RequestParam(value = "name")String name, Model model, HttpSession session) {
        model.addAttribute("findUser", chatService.findAllByName(name));
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));
        model.addAttribute("chatRoomMsg", chatService.findChat((String) session.getAttribute("login_id")));
        model.addAttribute("userNames", chatService.findChatName((String) session.getAttribute("login_id")));

        return "createChat";
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam(value = "member1", required = false) String member1,@RequestParam(value = "member2", required = false) String member2,
                           ChatRoom chatRoom, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        chatService.saveChatRoom(member1, member2, chatRoom);

        session.setAttribute("roomNum", chatService.findRoomNum(member1, member2));
        session.setAttribute("myId", member1);
        session.setAttribute("yourId", member2);
        session.setAttribute("yourName", chatService.findNameById(member1));

        model.addAttribute("roomNum", session.getAttribute("roomNum"));
        model.addAttribute("myId",  session.getAttribute("myId"));
        model.addAttribute("yourId",  session.getAttribute("yourId"));
        model.addAttribute("yourName",  session.getAttribute("yourName"));
        model.addAttribute("login_user", session.getAttribute("login_user"));
        model.addAttribute("chatRoomMsg", chatService.findChat(member1));
        model.addAttribute("userNames", chatService.findChatName(member1));

        return "chatting";
    }
}
