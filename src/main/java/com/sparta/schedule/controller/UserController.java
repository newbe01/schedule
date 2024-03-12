package com.sparta.schedule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.common.CommonResponse;
import com.sparta.schedule.dto.user.UserResponse;
import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.exception.ValidationException;
import com.sparta.schedule.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signup")
    public CommonResponse<UserResponse> userSignUp(
        @Parameter(description = "회원 requestDto")
        @RequestBody @Valid UserSignRequest request
        , BindingResult bindingResult
    ) throws JsonProcessingException {

        Map<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            throw new ValidationException(parsingJson(errorMap));
        }

        UserResponse userResponse = userService.userSignup(request);

        return CommonResponse.<UserResponse>builder()
            .data(userResponse)
            .message("회원가입 성공")
            .build();
    }

    private static String parsingJson(Map<String, String> errorMap) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(errorMap);
    }

}
