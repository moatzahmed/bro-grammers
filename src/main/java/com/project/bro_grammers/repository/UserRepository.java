package com.project.bro_grammers.repository;

import com.project.bro_grammers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
