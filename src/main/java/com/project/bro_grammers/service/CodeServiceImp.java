package com.project.bro_grammers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.exception.BadRequestException;
import com.project.bro_grammers.exception.NotAllowedIdException;
import com.project.bro_grammers.exception.ResourceNotFoundException;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.model.Role;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.repository.CodeRepository;
import com.project.bro_grammers.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CodeServiceImp implements CodeService {
    private final CodeRepository codeRepository;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public CodeServiceImp(CodeRepository codeRepository, UserService userService, ObjectMapper objectMapper, JwtUtil jwtUtil) {
        this.codeRepository = codeRepository;
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
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
    public List<Code> findCodesByAuthor(Long uploaderId) {
        return codeRepository.findByUploaderId(uploaderId);
    }


    @Override
    public Code find(Long id) {
        return codeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This code with ID " + id + " isn't found :("));
    }

    @Override
    public void deleteCode(Long codeId, String authHeader) {
        Long curUserId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            curUserId = jwtUtil.extractId(token);
        }
        User user = userService.find(curUserId);
        Code code = find(codeId);
        if (user.getRole() != Role.TEAM_LEAD && code.getUploaderId() != user.getId())
            throw new BadRequestException("Not Authorized !!");
        codeRepository.delete(code);
    }

    @Override
    public List<Code> myAllCodes(String authHeader) {
        Long curUserId = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            curUserId = jwtUtil.extractId(token);
        }
        return codeRepository.findByUploaderId(curUserId);
    }

    @Override
    public Code patchCode(Long id, Map<String, Object> updates) {
        Code code = find(id);
        if (updates.containsKey("id")) throw new NotAllowedIdException("Can't Add Id for The User manually !");
        ObjectNode codeNode = objectMapper.convertValue(code, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(updates, ObjectNode.class);
        codeNode.setAll(patchNode);
        return codeRepository.save(objectMapper.convertValue(codeNode, Code.class));
    }


}
