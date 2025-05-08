package com.project.bro_grammers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_file")
@Builder
@Data
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "uploader_id")
    private Integer uploaderId;

    @Column(name = "timestamp")
    private long timestamp;

    //    @Lob
    @Column(name = "content", columnDefinition = "BYTEA")
    private byte[] content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // ==================== Constructors ====================

    public Code() {
        this.timestamp = Instant.now().toEpochMilli();
        this.status = Status.PENDING;
    }

    public Code(String filename, Integer uploaderId, byte[] content) {
        this.filename = filename;
        this.uploaderId = uploaderId;
        this.timestamp = Instant.now().toEpochMilli();
        this.content = content;
        this.status = Status.PENDING;
    }

    // ==================== Getters and Setters ====================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
