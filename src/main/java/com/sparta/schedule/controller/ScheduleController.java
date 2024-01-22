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

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules() {
        return service.getSchedules().stream().map(ScheduleResponseDto::new).toList();
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return new ScheduleResponseDto(service.getSchedule(id));
    }


    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedules(@RequestBody ScheduleRequestDto requestDto) {
        return new ScheduleResponseDto(service.createSchedules(requestDto));
    }

}
