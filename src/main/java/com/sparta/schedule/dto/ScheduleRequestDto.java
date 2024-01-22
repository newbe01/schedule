package com.sparta.schedule.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class ScheduleRequestDto {

    private String title;
    private String contents;
    private String nickname;
    private String password;

}
