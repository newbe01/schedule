package com.sparta.schedule.repository;

import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepositoryQuery {

    Page<ScheduleListResponse> getAllSchedules(Pageable pageable);

}
