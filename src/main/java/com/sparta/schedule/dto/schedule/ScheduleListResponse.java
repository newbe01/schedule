package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
@Getter
public class ScheduleListResponse {

    private String username;

    private List<ScheduleResponseDto> schedules = new ArrayList<>();

    public ScheduleListResponse(User user) {
        this.username = user.getUsername();
        this.schedules.addAll(
                user.getScheduleList()
                        .stream()
                        .map(ScheduleResponseDto::new)
                        .sorted(Comparator.comparing(ScheduleResponseDto::getCreateAt).reversed())
                        .toList());
    }
}
