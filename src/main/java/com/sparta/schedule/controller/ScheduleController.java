package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping("schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return service.getSchedules();
    }

    @PostMapping("schedules")
    public ScheduleResponseDto createSchedules(@RequestBody ScheduleRequestDto requestDto) {
        System.out.println("requestDto = " + requestDto);
        return service.createSchedules(requestDto);
    }

}
