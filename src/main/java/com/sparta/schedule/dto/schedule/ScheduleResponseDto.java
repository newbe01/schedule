package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class ScheduleResponseDto {

    private String title;
    private String contents;
    private String username;

    private boolean CompletionYn;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUser().getUsername();
        this.CompletionYn = schedule.isCompletionYn();
        this.createAt = schedule.getCreatAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

}
