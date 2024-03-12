package com.sparta.schedule.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.schedule.domain.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@Builder
@Schema(description = "할일 responseDto")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponse {

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

    public ScheduleResponse(Schedule schedule) {
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUser().getUsername();
        this.CompletionYn = schedule.isCompletionYn();
        this.createAt = schedule.getCreatAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

    public static ScheduleResponse of(Schedule schedule) {
        return ScheduleResponse.builder()
            .title(schedule.getTitle())
            .contents(schedule.getContents())
            .username(schedule.getContents())
            .CompletionYn(schedule.isCompletionYn())
            .createAt(schedule.getCreatAt())
            .modifiedAt(schedule.getModifiedAt())
            .build();
    }

}
