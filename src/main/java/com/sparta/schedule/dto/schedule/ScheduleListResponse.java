package com.sparta.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(description = "할일 목록 조회 responseDto")
public class ScheduleListResponse {

    @Schema(description = "작성자")
    private String username;

    @Schema(description = "할일 목록")
    private List<ScheduleResponse> schedules = new ArrayList<>();

}
