package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository repository;


    public List<Schedule> getSchedules() {
        return repository.findAllByOrderByCreatAtDesc();
    }

    public Schedule getSchedule(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 일정"));
    }

    public Schedule createSchedules(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        return repository.save(schedule);
    }

}
