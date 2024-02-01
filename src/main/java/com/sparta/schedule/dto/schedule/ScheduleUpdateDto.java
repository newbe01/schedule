package com.sparta.schedule.dto.schedule;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScheduleUpdateDto {

    private String title;
    private String contents;
    private String nickname;
    private String password;

}
