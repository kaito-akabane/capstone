package com.example.ProjectCC.controller;

import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.UserRepository;
import com.example.ProjectCC.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

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

    @GetMapping("/setting")
    public String setting(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));

        return "setting";
    }

    @PostMapping("/setting")
    public String changeId(@RequestParam(value = "id") String id) {
        return "setting";
    }

    @PostMapping("/setting/check")
    @ResponseBody
    public String check(@RequestBody String id) {
        String checkId = joinService.checkId(id);

        return checkId;
    }
}