package com.sparta.schedule.controller;

import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody @Valid UserSignRequest request) {

        userService.userSignup(request);

        return ResponseEntity.ok("회원가입 성공");
    }

}
