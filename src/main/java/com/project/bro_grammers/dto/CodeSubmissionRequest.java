package com.project.bro_grammers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSubmissionRequest {
    private Long uploaderId;
    private MultipartFile file;
}
