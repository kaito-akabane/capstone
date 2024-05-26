package com.example.ProjectCC.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int roomNum;
    private String member1;
    private String member2;
}
