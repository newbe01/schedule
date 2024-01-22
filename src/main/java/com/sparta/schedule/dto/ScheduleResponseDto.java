package com.sparta.schedule.dto;

import com.sparta.schedule.domain.Schedule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String nickname;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContent();
        this.nickname = schedule.getContent();
    }

}
