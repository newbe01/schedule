package com.sparta.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Schema(description = "할일 수정삭제 requestDto")
public class ScheduleUpdate {

    @Schema(description = "할일 제목")
    private String title;

    @Schema(description = "할일 내용")
    private String contents;

}
