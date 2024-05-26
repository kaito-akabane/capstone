package com.example.ProjectCC.controller;

import com.example.ProjectCC.domain.Profile;
import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.service.ChatService;
import com.example.ProjectCC.service.LoginService;
import com.example.ProjectCC.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final ProfileService profileService;
    private final ChatService chatService;

    @GetMapping("login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String checkLogin(@RequestBody Map<String, String> map) {
        String checkLogin = loginService.checkLogin(map);

        return checkLogin;
    }

    @PostMapping("home")
    public String login(@RequestParam("id") String id, HttpServletRequest request, Model model) {
        Optional<User> user = loginService.findById(id);
        String userId = user.get().getId();

        HttpSession session = request.getSession();
        session.setAttribute("login_user", user.get().getName());
        session.setAttribute("login_id", userId);

        Optional<Profile> user1 = profileService.findByUserId(userId);
        model.addAttribute("chatRoom", chatService.findChat(userId));
        model.addAttribute("login_id", userId);
        model.addAttribute("chatRoomMsg", chatService.findChat(userId));
        model.addAttribute("userNames", chatService.findChatName((String) session.getAttribute("login_id")));

        if (!user1.isPresent()) {
            model.addAttribute("login_user", session.getAttribute("login_user"));
        }
        else {
            if(!user1.get().getStatus().isBlank()) {
                model.addAttribute("status", user1.get().getStatus());
            }
            if (!user1.get().getName().isBlank()) {
                model.addAttribute("login_user", user1.get().getName());
            } else {
                model.addAttribute("login_user", session.getAttribute("login_user"));
            }
        }
        return "home";
    }

    @GetMapping("/findPw")
    public String find() {
        return "findPw";
    }

    @PostMapping("/findPw")
    @ResponseBody
    public String findPw(@RequestBody String id) {
        String pw = loginService.findPw(id);

        return pw;
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}