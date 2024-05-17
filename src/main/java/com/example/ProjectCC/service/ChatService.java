package com.example.ProjectCC.service;

import com.example.ProjectCC.DTO.ChatRoom;
import com.example.ProjectCC.DTO.User;
import com.example.ProjectCC.repository.ChatRoomRepository;
import com.example.ProjectCC.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatService(UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<User> findAllByName(String name) {
        List<User> userList = userRepository.findAllByName(name);

        return userList;
    }

    public void saveChatRoom(String member, ChatRoom chatRoom) {
        if (member != null) {
            String members[] = member.split(" ");

            if(!chatRoomRepository.findByMember(member).isPresent()) {
                if (!chatRoomRepository.findByMember(members[1] + " " + members[0]).isPresent()) {
                    chatRoomRepository.save(chatRoom);
                }
            }
        }
    }

    public int roomNum(String member) {
        int roomNum = -1;

        if (member != null) {
            String members[] = member.split(" ");

            if (chatRoomRepository.findByMember(member).isPresent()) {
                roomNum = chatRoomRepository.findByMember(member).get().getId();
            } else {
                roomNum = chatRoomRepository.findByMember(members[1] + " " + members[0]).get().getId();
            }
        }

        return roomNum;
    }

    public String findById(String id) {
        String name = userRepository.findById(id).get().getName();

        return name;
    }
}
