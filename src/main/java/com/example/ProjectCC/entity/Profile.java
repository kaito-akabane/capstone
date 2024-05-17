package com.example.ProjectCC.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Profile  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String userId;
    private String name;
    private String status;
}
