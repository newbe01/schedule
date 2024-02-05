package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.domain.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@Schema(description = "할일 responseDto")
public class ScheduleResponseDto {

    @Schema(description = "할일 제목")
    private String title;

    @Schema(description = "할일 내용")
    private String contents;

    @Schema(description = "작성자")
    private String username;

    @Schema(description = "완료 여부")
    private boolean CompletionYn;

    @Schema(description = "작성일")
    private LocalDateTime createAt;

    @Schema(description = "수정일")
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
