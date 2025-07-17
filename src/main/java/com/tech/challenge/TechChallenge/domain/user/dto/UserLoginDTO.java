package com.tech.challenge.TechChallenge.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.StringJoiner;

@Getter
@Setter
public class UserLoginDTO implements Serializable {

    private String login;

    private String password;

    public UserLoginDTO() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserLoginDTO.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
