package com.sparta.schedule.controller;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@AuthenticationPrincipal UserDetails userDetails) {
        return scheduleService.getSchedules(userDetails.getUsername()).stream().map(ScheduleResponseDto::new).toList();
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return new ScheduleResponseDto(scheduleService.getSchedule(id));
    }


    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedules(@RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetails userDetails) {
        Schedule schedule = scheduleService.createSchedule(requestDto, userDetails.getUsername());

        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    @PutMapping("/schedules/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateDto requestDto) {
        return new ScheduleResponseDto(scheduleService.updateSchedule(id, requestDto));
    }

    @DeleteMapping("schedules/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto);
        return "삭제 성공";
    }

}
