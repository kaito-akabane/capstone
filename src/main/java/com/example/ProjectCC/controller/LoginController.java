package com.example.ProjectCC.controller;

import com.example.ProjectCC.entity.Profile;
import com.example.ProjectCC.entity.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository repository;
    private final ProfileRepository profileRepository;

    public LoginController(UserRepository repository, ProfileRepository profileRepository) {
        this.repository = repository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String checkLogin(@RequestBody Map<String, String> map) {
        Optional<User> userInfo = repository.findById(map.get("id"));
        if(!userInfo.isPresent()) {
            return "idNoExist";
        }
        else if(!map.get("pw").equals(userInfo.get().getPw())) {
            return "pwFailed";
        }
        return "correct";
    }

    @PostMapping("home")
    public String login(HttpServletRequest request, @RequestParam("id") String id, Model model) {
        Optional<User> user = repository.findById(id);
        HttpSession session = request.getSession();
        session.setAttribute("login_user", user.get().getName());
        session.setAttribute("login_id", user.get().getId());

        String userId = user.get().getId();
        Optional<Profile> user1 = profileRepository.findByUserId(userId);

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
        Optional<User> member = repository.findById(id);
        //가입된 회원일 경우
        if (!member.isPresent()) {
            return "wrong";
        } else {
            String pw = member.get().getPw();
            String enPw = pw.substring(0, pw.length() / 2);
            for(int i = pw.length() - enPw.length(); i > 0; i--)
                enPw += "*";
            return enPw;
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}