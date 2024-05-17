package com.example.ProjectCC.controller;

import com.example.ProjectCC.DTO.Profile;
import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
import com.example.ProjectCC.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("login_id", session.getAttribute("login_id"));
        model.addAttribute("login_user", session.getAttribute("login_user"));

        return "profile";
    }

    @PostMapping("/saveImage")
    @Transactional
    public String saveImage(@RequestParam(value="name") String name, @RequestParam(value="status") String status,
                            @RequestParam(value="image") MultipartFile image, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("login_id");
        String path = "userImg/" + userId + ".png";

        profileService.saveImage(image, path, userId, name, status);

        model.addAttribute("login_id", userId);
        if(!status.isBlank()) {
            model.addAttribute("status", status);
        }
        if (!name.isBlank()) {
            model.addAttribute("login_user", name);
        } else {
            model.addAttribute("login_user", session.getAttribute("login_user"));
        }

        return "home";
    }
}
