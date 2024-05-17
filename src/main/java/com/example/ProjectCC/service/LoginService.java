package com.example.ProjectCC.service;

import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String checkLogin(Map<String, String> map) {
        Optional<User> user = userRepository.findById(map.get("id"));

        if(!user.isPresent()) {
            return "idNoExist";
        }
        else if(!map.get("pw").equals(user.get().getPw())) {
            return "pwFailed";
        }
        else {
            return "correct";
        }
    }

    public Optional<User> findById(String id) {
        Optional<User> user = userRepository.findById(id);

        return user;
    }

    public String findPw(String id) {
        Optional<User> member = userRepository.findById(id);

        if (!member.isPresent()) {
            return "wrong";     //가입된 회원일 경우
        }
        else {
            String pw = member.get().getPw();
            String enPw = pw.substring(0, pw.length() / 2);

            for(int i = pw.length() - enPw.length(); i > 0; i--)
                enPw += "*";

            return enPw;
        }
    }
}
