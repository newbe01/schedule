package com.sparta.schedule.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UserSignRequest {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

}
