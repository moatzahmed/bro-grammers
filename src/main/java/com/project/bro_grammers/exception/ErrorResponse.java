package com.project.bro_grammers.exception;

import java.time.Instant;
import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int status;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
