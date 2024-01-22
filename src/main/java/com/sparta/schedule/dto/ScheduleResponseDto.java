package com.sparta.schedule.dto;

import com.sparta.schedule.domain.Schedule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String nickname;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.nickname = schedule.getNickname();
        this.createAt = schedule.getCreatAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
