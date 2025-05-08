package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CodeService {
    Code uploadCode(CodeSubmissionRequest codeSubmissionRequest);

    List<Code> findAll();

    List<Code> findCodesByAuthor(Long uploaderId);

    Code find(Long id);

    void deleteCode(Long id);

    Code patchCode(Long id, @RequestBody Map<String, Object> updates);

    List<Code> myAllCodes(String authHeader);
}
