package com.example.ProjectCC.repository;


import com.example.ProjectCC.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public Message save(Message message);
    public List<Message> findByRoomNumOrderByIdDesc(int roomNum);
    public List<Message> findByRoomNumOrderByIdAsc(int roomNum);
}