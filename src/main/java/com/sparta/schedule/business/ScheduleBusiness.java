package com.sparta.schedule.business;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleBusiness {

    private final ScheduleRepository scheduleRepository;

    public Schedule findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException("없는 일정입니다."));
    }

    public Page<ScheduleListResponse> getAllSchedules(Pageable pageable) {
        return scheduleRepository.getAllSchedules(pageable);
    }

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepository.saveAndFlush(schedule);
    }

}
