package com.example.ProjectCC.repository;


import com.example.ProjectCC.DTO.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public Message save(Message message);
}