package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.model.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CodeService {
    Code uploadCode(CodeSubmissionRequest codeSubmissionRequest);

    List<Code> findAll();

    List<Code> findCodesByAuthor(Integer uploaderId);

    Code find(Integer id);

    void deleteCode(Integer id);
}
