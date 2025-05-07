package com.project.bro_grammers.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "commenter_id")
    private Integer commenterId;

    @Column(name = "code_id")
    private Integer codeId;

}
