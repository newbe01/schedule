package com.sparta.schedule.dto.user;

import com.sparta.schedule.domain.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private String username;

    public static UserResponse of(User user) {
        return UserResponse.builder()
            .username(user.getUsername())
            .build();
    }

}
