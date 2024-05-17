package com.example.ProjectCC.controller;

import com.example.ProjectCC.entity.ChatRoom;
import com.example.ProjectCC.repository.ChatRoomRepository;
import com.example.ProjectCC.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ChatController {

    private final UserRepository repository;
    private final ChatRoomRepository chatRoomRepository;
    private final HttpSession httpSession;

    public ChatController(UserRepository repository, ChatRoomRepository chatRoomRepository, HttpSession httpSession) {
        this.repository = repository;
        this.chatRoomRepository = chatRoomRepository;
        this.httpSession = httpSession;
    }

    @GetMapping("/createChat")
    public String createChat(HttpSession session, Model model) {
        model.addAttribute("login_id", session.getAttribute("login_id"));
        return "createChat";
    }

    @GetMapping("/findUser")
    public String findUser(@RequestParam(value = "name")String name, Model model, HttpSession session) {
        model.addAttribute("findUser", repository.findAllByName(name));
        model.addAttribute("login_id", session.getAttribute("login_id"));
        return "createChat";
    }

    @GetMapping("/chatting")
    public String chatting(@RequestParam(value = "member", required = false) String member, Model model, ChatRoom chatRoom, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (member != null) {
            String s[] = member.split(" ");

            int roomNum = -1;
            if(!chatRoomRepository.findByMember(member).isPresent()) {
                if (!chatRoomRepository.findByMember(s[1] + " " + s[0]).isPresent()) {
                    chatRoomRepository.save(chatRoom);
                }
            }
            if (chatRoomRepository.findByMember(member).isPresent()){
                roomNum = chatRoomRepository.findByMember(member).get().getId();
            }
            else {
                roomNum = chatRoomRepository.findByMember(s[1] + " " + s[0]).get().getId();
            }
            session.setAttribute("roomNum", roomNum);
            session.setAttribute("myId", s[0]);
            session.setAttribute("yourId", s[1]);
            session.setAttribute("yourName", repository.findById(s[1]).get().getName());
        }
        model.addAttribute("roomNum", session.getAttribute("roomNum"));
        model.addAttribute("myId",  session.getAttribute("myId"));
        model.addAttribute("yourId",  session.getAttribute("yourId"));
        model.addAttribute("yourName",  session.getAttribute("yourName"));
        return "chatting";
    }
}
