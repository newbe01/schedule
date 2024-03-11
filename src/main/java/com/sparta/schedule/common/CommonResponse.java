package com.sparta.schedule.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private T data;
    private String message;

    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

}
