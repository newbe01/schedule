package com.sparta.schedule.dto.schedule;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "할일 requestDto")
public class ScheduleRequest {

    @Schema(description = "할일 제목")
    private String title;

    @Schema(description = "할일 내용")
    private String contents;

}
