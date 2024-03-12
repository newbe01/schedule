package com.sparta.schedule.controller;

import com.sparta.schedule.common.CommonResponse;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public CommonResponse<ScheduleResponse> createSchedules(
        @Parameter(description = "할일 requestDto")
        @RequestBody ScheduleRequest requestDto,

        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ScheduleResponse response = scheduleService.createSchedule(requestDto,
            userDetails.getUser());

        return CommonResponse.<ScheduleResponse>builder()
            .data(response)
            .build();
    }

    @Operation(summary = "find schedule", description = "할일 조회", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @GetMapping("/schedules/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<ScheduleResponse> getSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id
    ) {
        ScheduleResponse response = scheduleService.getSchedule(id);

        return CommonResponse.<ScheduleResponse>builder()
            .data(response)
            .build();
    }

    @Operation(summary = "find schedules", description = "할일 목록 조회", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @GetMapping("/schedules")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Page<ScheduleListResponse>> getSchedules(Pageable pageable) {
        Page<ScheduleListResponse> responses = scheduleService.getSchedules(pageable);

        return CommonResponse.<Page<ScheduleListResponse>>builder()
            .data(responses)
            .build();
    }

    @Operation(summary = "update schedule", description = "할일 수정", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("/schedules/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<ScheduleResponse> updateSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id,

        @Parameter(description = "할일 requestDto")
        @RequestBody ScheduleUpdate requestDto,

        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ScheduleResponse response = scheduleService.updateSchedule(
            id, requestDto, userDetails.getUser()
        );

        return CommonResponse.<ScheduleResponse>builder()
            .data(response)
            .build();

    }

    @Operation(summary = "complete schedule", description = "할일 완료", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("schedules/{id}/complete")
    public CommonResponse<ScheduleResponse> completeSchedule(
        @Parameter(description = "할일 번호")
        @PathVariable Long id,

        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ScheduleResponse response = scheduleService.completeSchedule(id, userDetails.getUser());
        return CommonResponse.<ScheduleResponse>builder()
            .message("할일 완료")
            .data(response)
            .build();
    }

}
