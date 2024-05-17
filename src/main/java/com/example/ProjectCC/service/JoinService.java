package com.example.ProjectCC.service;

import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class JoinService {

    private final UserRepository userRepository;

    public JoinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public String checkIdDuplicate(String userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            return "available";
        }
        else {
            return "duplicate";
        }
    }

    public String checkId(String id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            return "exist";
        }
        else {
            return "idNoExist";
        }
    }
}
