package com.sparta.schedule.controller;

import com.sparta.schedule.common.CommonResponse;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleResponseDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "schedule", description = "할일 API")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "add schedule", description = "할일 추가", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PostMapping("/schedules")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<ScheduleResponseDto> createSchedules(
        @Parameter(description = "할일 requestDto")
        @RequestBody ScheduleRequestDto requestDto,

        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.createSchedule(requestDto, userDetails.getUser());
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return new CommonResponse<>(scheduleResponseDto);
    }

    @Operation(summary = "find schedule", description = "할일 조회", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @GetMapping("/schedules/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<ScheduleResponseDto> getSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id
    ) {
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(
            scheduleService.getSchedule(id));

        return new CommonResponse<>(scheduleResponseDto);
    }

    @Operation(summary = "find schedules", description = "할일 목록 조회", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @GetMapping("/schedules")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<ScheduleListResponse>> getSchedules() {
        List<User> schedules = scheduleService.getSchedules();
        List<ScheduleListResponse> listResponses = schedules.stream().map(ScheduleListResponse::new)
            .toList();

        return new CommonResponse<>(listResponses);
    }

    @Operation(summary = "update schedule", description = "할일 수정", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("/schedules/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<ScheduleResponseDto> updateSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id,

        @Parameter(description = "할일 requestDto")
        @RequestBody ScheduleUpdateDto requestDto,

        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Schedule schedule = scheduleService.updateSchedule(id, requestDto, userDetails.getUser());
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return new CommonResponse<>(scheduleResponseDto);
    }

    @Operation(summary = "complete schedule", description = "할일 완료", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("schedules/{id}/complete")
    public CommonResponse<Void> completeSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id,

        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        scheduleService.completeSchedule(id, userDetails.getUser());
        return new CommonResponse<>("할일 완료");
    }

}
