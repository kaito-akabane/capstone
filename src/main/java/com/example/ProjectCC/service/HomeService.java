package com.example.ProjectCC.service;

import com.example.ProjectCC.DTO.Profile;
import com.example.ProjectCC.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService  {

    private final ProfileRepository profileRepository;

    public HomeService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> findProfile(String userId) {
        Optional<Profile> user = profileRepository.findByUserId(userId);
        return user;
    }
}
