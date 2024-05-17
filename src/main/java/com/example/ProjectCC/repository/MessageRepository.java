package com.example.ProjectCC.repository;


import com.example.ProjectCC.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    public Message save(Message message);
}