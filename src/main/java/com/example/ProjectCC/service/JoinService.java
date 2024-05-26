package com.example.ProjectCC.service;

import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;

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
}
