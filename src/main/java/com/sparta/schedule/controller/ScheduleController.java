package com.sparta.schedule.controller;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedules(
            @RequestBody ScheduleRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.createSchedule(requestDto, userDetails.getUser());
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(scheduleService.getSchedule(id));

        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleListResponse>> getSchedules() {
        List<User> schedules = scheduleService.getSchedules();
        List<ScheduleListResponse> listResponses = schedules.stream().map(ScheduleListResponse::new).toList();

        return new ResponseEntity<>(listResponses, HttpStatus.OK);
    }


    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.updateSchedule(id, requestDto, userDetails.getUser());
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return new ResponseEntity<>(scheduleResponseDto,HttpStatus.OK);
    }

    @PutMapping("schedules/{id}/complete")
    public ResponseEntity<String> completeSchedule(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.completeSchedule(id, userDetails.getUser());
        return ResponseEntity.ok("일정 완료");
    }

}
