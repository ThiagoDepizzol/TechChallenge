package com.tech.challenge.TechChallenge.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
public class MessageResponseDTO {
    private String message;

    public MessageResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MessageResponseDTO.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .toString();
    }
}
