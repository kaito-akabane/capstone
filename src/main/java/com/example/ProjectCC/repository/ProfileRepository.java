package com.example.ProjectCC.repository;

import com.example.ProjectCC.DTO.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile save(Profile profile);
    Optional<Profile> findByUserId(String userId);
}
