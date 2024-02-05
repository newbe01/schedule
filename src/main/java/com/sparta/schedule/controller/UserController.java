package com.sparta.schedule.controller;

import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "user", description = "회원 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "sign up", description = "회원 가입", responses = {
            @ApiResponse(responseCode = "200", description = "successful"),
            @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(
            @Parameter(description = "회원 requestDto")
            @RequestBody @Valid UserSignRequest request
            , BindingResult bindingResult
    ) {
        String errorMessages = "";
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMessages += fieldError.getField() + " : " + fieldError.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        userService.userSignup(request);

        return ResponseEntity.ok("회원가입 성공");
    }

}
