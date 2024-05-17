package com.example.ProjectCC.controller;

import com.example.ProjectCC.DTO.Profile;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.service.HomeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session==null) {
            return "redirect:/login";
        }
        else {
            String userId = (String) session.getAttribute("login_id");
            Optional<Profile> user = homeService.findProfile(userId);

            model.addAttribute("login_id", userId);
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
