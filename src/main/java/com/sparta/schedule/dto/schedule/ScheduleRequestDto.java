package com.sparta.schedule.dto.schedule;


import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    private String title;
    private String contents;

}
