package com.example.ProjectCC.service;

import com.example.ProjectCC.domain.ChatRoom;
import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

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
        return userRepository.findById(id);
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
