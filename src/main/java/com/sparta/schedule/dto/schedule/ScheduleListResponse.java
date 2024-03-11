package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
@Getter
@Schema(description = "할일 목록 조회 responseDto")
public class ScheduleListResponse {

    @Schema(description = "작성자")
    private String username;

    @Schema(description = "할일 목록")
    private List<ScheduleResponse> schedules = new ArrayList<>();

    public ScheduleListResponse(User user) {
        this.username = user.getUsername();
        this.schedules.addAll(
                user.getScheduleList()
                        .stream()
                        .map(ScheduleResponse::new)
                        .sorted(Comparator.comparing(
                                ScheduleResponse::getCreateAt, Comparator.nullsFirst(Comparator.reverseOrder())))
                        .toList());
    }
}
