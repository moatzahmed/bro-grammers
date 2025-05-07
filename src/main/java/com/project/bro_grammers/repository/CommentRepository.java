package com.project.bro_grammers.repository;

import com.project.bro_grammers.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {


}
