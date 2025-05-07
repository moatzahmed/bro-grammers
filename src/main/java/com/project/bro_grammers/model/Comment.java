package com.project.bro_grammers.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "commenter_id")
    private Long commenterId;

    @Column(name = "code_id")
    private Long codeId;
    @Column(name = "content")
    private String content;

}
