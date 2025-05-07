package com.project.bro_grammers.repository;

import com.project.bro_grammers.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CodeRepository extends JpaRepository<Code,Integer> {
    List<Code> findByUploaderId(Integer uploaderId);
}
