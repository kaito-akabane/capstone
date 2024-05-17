package com.example.ProjectCC.controller;

import com.example.ProjectCC.entity.User;
import com.example.ProjectCC.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class JoinController {

    private final UserRepository repository;

    public JoinController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("join")
    public String joinUser(User user){
        repository.save(user);
        return "login";
    }

    @PostMapping("/duplicate")
    @ResponseBody
    public String searchUser(@RequestBody String id) {
        Optional<User> userId = repository.findById(id);
        if(userId.isEmpty()) {
            return "available";
        }
        return "duplicate";
    }

}