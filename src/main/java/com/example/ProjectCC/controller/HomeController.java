package com.example.ProjectCC.controller;

import com.example.ProjectCC.entity.Profile;
import com.example.ProjectCC.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class HomeController {

    private final ProfileRepository profileRepository;

    public HomeController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session==null) {
            return "redirect:/login";
        }
        else {
            String userId = (String) session.getAttribute("login_id");
            Optional<Profile> user = profileRepository.findByUserId(userId);

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
