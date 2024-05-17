package com.example.ProjectCC.controller;

import com.example.ProjectCC.entity.Profile;
import com.example.ProjectCC.entity.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
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

    private final UserRepository repository;
    private final ProfileRepository profileRepository;

    public ProfileController(UserRepository repository, ProfileRepository profileRepository) {
        this.repository = repository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("login_id", session.getAttribute("login_id"));
        return "profile";
    }

    @PostMapping("/saveImage")
    @Transactional
    public String saveImage(@RequestParam(value="name") String name, @RequestParam(value="status") String status,
                            @RequestParam(value="image") MultipartFile image, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("login_id");
        String path = "userImg/" + userId + ".png";

        if(!image.isEmpty()){
            try {
                Path imagePath = Paths.get("src/main/resources/static/" + path);

                // 파일 존재 시 삭제
                Files.deleteIfExists(imagePath);
                // 파일 저장
                Files.write(imagePath, image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Optional<Profile> user = profileRepository.findByUserId(userId);

        if (user.isPresent()) {
            Profile profile = new Profile();
            profile.setName(name);
            profile.setStatus(status);
            profile.setUserId(userId);
            profile.setNo(user.get().getNo());
            profileRepository.save(profile);
        }
        else {
            Profile profile = new Profile();
            profile.setName(name);
            profile.setStatus(status);
            profile.setUserId(userId);
            profileRepository.save(profile);
        }

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

    @GetMapping("/setting")
    public String setting(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("login_id", session.getAttribute("login_id"));
        return "setting";
    }

    @PostMapping("/setting")
    public String changeId(@RequestParam(value = "id") String id) {
        return "setting";
    }

    @PostMapping("/setting/check")
    @ResponseBody
    public String check(@RequestBody String id) {
        Optional<User> user = repository.findById(id);

        if(user.isPresent()) {
            return "exist";
        }
        else {
            return "idNoExist";
        }
    }
}
