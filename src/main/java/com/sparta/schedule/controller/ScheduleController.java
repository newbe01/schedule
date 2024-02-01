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

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleListResponse>> getSchedules() {
        return new ResponseEntity<>(scheduleService.getSchedules().stream().map(ScheduleListResponse::new).toList()
                , HttpStatus.OK);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        return new ResponseEntity<>(new ScheduleResponseDto(scheduleService.getSchedule(id)), HttpStatus.OK);
    }


    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedules(@RequestBody ScheduleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Schedule schedule = scheduleService.createSchedule(requestDto, userDetails.getUser());

        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id,
                                              @RequestBody ScheduleUpdateDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(
                new ScheduleResponseDto(scheduleService.updateSchedule(id, requestDto, userDetails.getUser())),
                HttpStatus.OK);
    }

    @PutMapping("schedules/{id}/complete")
    public ResponseEntity<String> completeSchedule(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.completeSchedule(id, userDetails.getUser());
        return ResponseEntity.ok("일정 완료");
    }

    @Deprecated
    @DeleteMapping("schedules/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestBody ScheduleUpdateDto requestDto) {
        scheduleService.deleteSchedule(id, requestDto);
        return "삭제 성공";
    }

}
