package com.example.ProjectCC.controller;

import com.example.ProjectCC.DTO.Profile;
import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
import com.example.ProjectCC.service.LoginService;
import com.example.ProjectCC.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final ProfileService profileService;

    public LoginController(LoginService loginService, ProfileService profileService) {
        this.loginService = loginService;
        this.profileService = profileService;
    }

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

        HttpSession session = request.getSession();
        session.setAttribute("login_user", user.get().getName());
        session.setAttribute("login_id", user.get().getId());

        String userId = user.get().getId();
        Optional<Profile> user1 = profileService.findByUserId(userId);

        model.addAttribute("login_id", userId);
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