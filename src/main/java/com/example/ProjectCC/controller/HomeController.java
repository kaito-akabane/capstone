package com.example.ProjectCC.controller;

import com.example.ProjectCC.domain.Profile;
import com.example.ProjectCC.service.ChatService;
import com.example.ProjectCC.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProfileService profileService;
    private final ChatService chatService;

    @GetMapping(value = {"/", "/home"})
    public String homePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session==null) {
            return "redirect:/login";
        }
        else {
            String userId = (String) session.getAttribute("login_id");
            Optional<Profile> user = profileService.findByUserId(userId);

            model.addAttribute("login_id", userId);
            model.addAttribute("chatRoomMsg", chatService.findChat(userId));
            model.addAttribute("userNames", chatService.findChatName(userId));

            if (!user.isPresent()) {
                model.addAttribute("login_user", session.getAttribute("login_user"));
            }
            else {
                if(!user.get().getStatus().isBlank()) {
                    model.addAttribute("status", user.get().getStatus());
                }
                if (!user.get().getName().isBlank()) {
                    model.addAttribute("login_user", user.get().getName());
                } else {
                    model.addAttribute("login_user", session.getAttribute("login_user"));
                }
            }
        }
        return "home";
    }
}
