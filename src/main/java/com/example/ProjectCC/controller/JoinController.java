package com.example.ProjectCC.controller;

import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.service.ChatService;
import com.example.ProjectCC.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("join")
    public String joinUser(User user){
        joinService.join(user);

        return "login";
    }

    @PostMapping("/duplicate")
    @ResponseBody
    public String searchUser(@RequestBody String id) {
        String checkDup = joinService.checkIdDuplicate(id);

        return checkDup;
    }
}