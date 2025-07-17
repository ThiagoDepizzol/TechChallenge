package com.tech.challenge.TechChallenge.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.StringJoiner;

@Getter
@Setter
public class ErrorResponseDTO {

    private Instant timestamp;

    private int status;

    private String error;

    private String message;

    private String path;

    public ErrorResponseDTO(final HttpStatus status, final String message, final String path) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorResponseDTO.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("status=" + status)
                .add("error='" + error + "'")
                .add("message='" + message + "'")
                .add("path='" + path + "'")
                .toString();
    }
}
