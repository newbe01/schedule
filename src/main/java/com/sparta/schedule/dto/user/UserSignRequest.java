package com.sparta.schedule.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 requestDto")
public class UserSignRequest {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "영소문자와 숫자만, 4~10길이만 허용")
    @Schema(description = "회원 아이디")
    private String username;

    @Schema(description = "회원 비밀번호")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "영대소문자와 숫자만, 8~15길이만 허용")
    private String password;

}
