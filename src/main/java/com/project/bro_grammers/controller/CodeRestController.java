package com.project.bro_grammers.controller;

import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CodeRestController {
    private CodeService codeService;


    @Autowired
    CodeRestController(CodeService codeService) {
        this.codeService = codeService;
    }


    @PostMapping("/codes")
    public Code uploadCode(@RequestParam("file") MultipartFile file, @RequestParam("uploaderId") Integer uploaderId) {
        return codeService.uploadCode(new CodeSubmissionRequest(uploaderId, file));
    }


    @GetMapping("/codes")
    public ResponseEntity<List<Code>> findAllCodes() {
        List<Code> codes = codeService.findAll();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/codes/{id}")
    public ResponseEntity<Code> downloadFile(@PathVariable Integer id) {
        Code code = codeService.find(id);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/codes/authors/{uploaderId}")
    public List<Code> codeByAuthor(@PathVariable Integer uploaderId) {
        return codeService.findCodesByAuthor(uploaderId);
    }

    @DeleteMapping("/codes/{codeId}")
    public ResponseEntity<Void> deleteCode(@PathVariable Integer codeId) {
        codeService.deleteCode(codeId);
        return ResponseEntity.noContent().build();
    }


}
