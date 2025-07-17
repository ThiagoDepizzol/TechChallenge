package com.tech.challenge.TechChallenge.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.StringJoiner;

@Getter
@Setter
public class UserResetPasswordDTO implements Serializable {

    private String login;

    private String oldPassword;

    private String newPassword;

    public UserResetPasswordDTO() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserResetPasswordDTO.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                //                .add("oldPassword='" + oldPassword + "'")
                //                .add("newPassword='" + newPassword + "'")
                .toString();
    }
}
