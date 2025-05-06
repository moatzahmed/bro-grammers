package com.project.bro_grammers.service;

import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.exception.NotAllowedIdException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CodeServiceImp implements CodeService {
    private CodeRepository codeRepository;
    private UserService userService;

    @Autowired
    public CodeServiceImp(CodeRepository codeRepository, UserService userService) {
        this.codeRepository = codeRepository;
        this.userService = userService;
    }


    @Override
    public Code uploadCode(CodeSubmissionRequest codeSubmissionRequest) {
        if (codeSubmissionRequest.getUploaderId() == null || userService.find(codeSubmissionRequest.getUploaderId()) == null)
            throw new ResourceNotFoundException("User Not found !");

        Code code = new Code();
        code.setUploaderId(codeSubmissionRequest.getUploaderId());
        MultipartFile file = codeSubmissionRequest.getFile();
        code.setFilename(file.getOriginalFilename());
        try {
            code.setContent(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file content", e);
        }
        return codeRepository.save(code);
    }

    @Override
    public List<Code> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public List<Code> findCodesByAuthor(Integer uploaderId) {
        return codeRepository.findByUploaderId(uploaderId);
    }


    @Override
    public Code find(Integer id) {
        return codeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This code with ID " + id + " isn't found :("));
    }

    @Override
    public void deleteCode(Integer id) {
        Code code = find(id);
        codeRepository.delete(code);
    }
}
