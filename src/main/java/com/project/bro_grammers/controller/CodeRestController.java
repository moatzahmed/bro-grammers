package com.project.bro_grammers.controller;

import com.project.bro_grammers.dto.CodeSubmissionRequest;
import com.project.bro_grammers.model.Code;
import com.project.bro_grammers.model.Comment;
import com.project.bro_grammers.model.User;
import com.project.bro_grammers.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CodeRestController {
    private final CodeService codeService;


    @Autowired
    CodeRestController(CodeService codeService) {
        this.codeService = codeService;
    }


    @PostMapping("/codes")
    public Code uploadCode(@RequestParam("file") MultipartFile file, @RequestParam("uploaderId") Long uploaderId) {
        return codeService.uploadCode(new CodeSubmissionRequest(uploaderId, file));
    }


    @GetMapping("/codes")
    public ResponseEntity<List<Code>> findAllCodes() {
        List<Code> codes = codeService.findAll();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/codes/{codeId}")
    public ResponseEntity<Code> downloadFile(@PathVariable Long codeId) {
        Code code = codeService.find(codeId);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/codes/authors/{uploaderId}")
    public List<Code> codeByAuthor(@PathVariable Long uploaderId) {
        return codeService.findCodesByAuthor(uploaderId);
    }

    @DeleteMapping("/codes/{codeId}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long codeId) {
        codeService.deleteCode(codeId);
        return ResponseEntity.noContent().build();
    }

    //add this
    @PatchMapping("/codes/{id}")
    public Code patchCode(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return codeService.patchCode(id, updates);
    }

    @GetMapping("codes/myCodes")
    public List<Code> myCodes(@RequestHeader("Authorization") String authHeader) {
        return codeService.myAllCodes(authHeader);
    }
}
