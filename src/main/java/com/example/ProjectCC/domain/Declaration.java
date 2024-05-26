package com.example.ProjectCC.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Declaration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(name = "신고자")
    private String name;

    @Column(name = "신고대상")
    private String target;

    @Column(name = "욕설")
    private String abuse;

    @Column(name = "성적발언")
    private String sexual;

    @Column(name = "인격모독")
    private String insult;

    @Column(name = "신고내용")
    private String content;
}
