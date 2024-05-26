package com.example.ProjectCC.service;

import com.example.ProjectCC.domain.Profile;
import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.repository.ProfileRepository;
import com.example.ProjectCC.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public Optional<Profile> findByUserId(String Id) {
       return profileRepository.findByUserId(Id);
    }

    public void saveImage(MultipartFile image, String path, String id, String name, String status) {
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

        Optional<Profile> user = profileRepository.findByUserId(id);

        if (user.isPresent()) {
            Profile profile = new Profile();
            profile.setName(name);
            profile.setStatus(status);
            profile.setUserId(id);
            profile.setNo(user.get().getNo());
            profileRepository.save(profile);
        }
        else {
            Profile profile = new Profile();
            profile.setName(name);
            profile.setStatus(status);
            profile.setUserId(id);
            profileRepository.save(profile);
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
