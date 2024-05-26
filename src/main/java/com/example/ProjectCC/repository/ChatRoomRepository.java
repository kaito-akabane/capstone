package com.example.ProjectCC.repository;


import com.example.ProjectCC.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    public ChatRoom save(ChatRoom chatRoom);
    public Optional<ChatRoom> findByMember1AndMember2(String member1, String member2);
    public ChatRoom findByRoomNum(int roomNumber);
    public List<ChatRoom> findByMember1(String member1);
    public List<ChatRoom> findByMember2(String member2);
}