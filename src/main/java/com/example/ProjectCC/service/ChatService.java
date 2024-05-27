package com.example.ProjectCC.service;

import com.example.ProjectCC.domain.ChatRoom;
import com.example.ProjectCC.domain.Declaration;
import com.example.ProjectCC.domain.Message;
import com.example.ProjectCC.domain.User;
import com.example.ProjectCC.repository.ChatRoomRepository;
import com.example.ProjectCC.repository.DeclarationRepository;
import com.example.ProjectCC.repository.MessageRepository;
import com.example.ProjectCC.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final DeclarationRepository declarationRepository;
    private final MessageRepository messageRepository;

    public List<User> findAllByName(String name) {
        return userRepository.findAllByName(name);
    }

    public void saveChatRoom(String member1, String member2, ChatRoom chatRoom) {
        if (member1 != null && member2 != null) {
            if (!chatRoomRepository.findByMember1AndMember2(member1, member2).isPresent()) {
                if (!chatRoomRepository.findByMember1AndMember2(member2, member1).isPresent()) {
                    chatRoomRepository.save(chatRoom);
                }
            }
        }
    }

    public String findNameById(String id) {
        return userRepository.findById(id).get().getName();
    }

    public void saveDecla(Map<String, String> map) {
        Declaration de = new Declaration();
        de.setName(map.get("신고자"));
        de.setTarget(map.get("신고대상"));
        de.setAbuse(map.get("abuse"));
        de.setSexual(map.get("sexual"));
        de.setInsult(map.get("insult"));
        de.setContent(map.get("content"));

        declarationRepository.save(de);
    }

    public int findRoomNum(String member1, String member2) {
        int roomNum = -1;
        if (chatRoomRepository.findByMember1AndMember2(member1, member2).isPresent()) {
            roomNum = chatRoomRepository.findByMember1AndMember2(member1, member2).get().getRoomNum();
        } else {
            roomNum = chatRoomRepository.findByMember1AndMember2(member2, member1).get().getRoomNum();
        }
        return roomNum;
    }

    public Map<String, String> findChat(String userId) {
        List<Integer> roomNum = new ArrayList<>();
        Map<String, String> roomNumMessage = new HashMap<>();

        List<String> chatMembers = findMemberList(userId);

        for (String yourId : chatMembers) {
            roomNum.add(findRoomNum(userId, yourId));
        }

        List<String> messages = findMessageByRoomNumber(roomNum);

        for (int i = 0; i < messages.size(); i++) {
            roomNumMessage.put(chatMembers.get(i), messages.get(i));
        }

        return roomNumMessage;
    }

    public List<String> findMemberList(String userId) {
        List<String> chatMembers = new ArrayList<>();

        if (chatRoomRepository.findByMember1(userId) != null) {
            List<ChatRoom> list = chatRoomRepository.findByMember1(userId);

            for (ChatRoom chatRoom : list) {
                chatMembers.add(chatRoom.getMember2());
            }
        }
        if (chatRoomRepository.findByMember2(userId) != null) {
            List<ChatRoom> list = chatRoomRepository.findByMember2(userId);

            for (ChatRoom chatRoom : list) {
                chatMembers.add(chatRoom.getMember1());
            }
        }

        return chatMembers;
    }

    public Map<String, String> findChatName(String userId) {
        Map<String, String> userName = new HashMap<>();

        List<String> memberList = findMemberList(userId);

        for (int i = 0; i < memberList.size(); i++) {
            userName.put(memberList.get(i), findNameById(memberList.get(i)));
        }

        return userName;
    }

    public List<String> findMessageByRoomNumber(List<Integer> roomNum) {
        List<String> messageList = new ArrayList<>();

        for(Integer room : roomNum) {
            List<Message> messages = messageRepository.findByRoomNumOrderByIdDesc(room);
            if (!messages.isEmpty()) { // 리스트가 비어 있지 않은지 확인
                messageList.add(messages.get(0).getContent());
            }
            else {
                messageList.add("");
            }
        }
        return messageList;
    }

    public List<Message> findSavedMessage(String member1, String member2) {
        return messageRepository.findByRoomNumOrderByIdAsc(findRoomNum(member1, member2));
    }
}