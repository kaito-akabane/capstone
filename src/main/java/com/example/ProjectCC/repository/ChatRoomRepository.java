package com.example.ProjectCC.repository;


import com.example.ProjectCC.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    public ChatRoom save(String member);

    public Optional<ChatRoom> findByMember(String member);
}