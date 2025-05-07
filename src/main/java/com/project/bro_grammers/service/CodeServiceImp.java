package com.project.bro_grammers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.exception.NotAllowedIdException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CodeServiceImp implements CodeService {
    private final CodeRepository codeRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CodeServiceImp(CodeRepository codeRepository, UserService userService, ObjectMapper objectMapper) {
        this.codeRepository = codeRepository;
        this.userService = userService;
        this.objectMapper = objectMapper;
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

    @Override
    public Code patchCode(Integer id, Map<String, Object> updates) {
        Code code = find(id);
        if (updates.containsKey("id")) throw new NotAllowedIdException("Can't Add Id for The User manually !");
        ObjectNode codeNode = objectMapper.convertValue(code, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(updates, ObjectNode.class);
        codeNode.setAll(patchNode);
        return objectMapper.convertValue(codeNode, Code.class);
    }


}
