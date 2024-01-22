package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.dto.ScheduleUpdateDto;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Schedule createSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto);

        return repository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Long id, ScheduleUpdateDto requestDto) {
        Schedule schedule = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 일정"));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        schedule.updateSchedule(requestDto);

        return schedule;
    }

    public void deleteSchedule(Long id, ScheduleUpdateDto requestDto) {
        Schedule schedule = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 일정"));

        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        repository.delete(schedule);
    }
}
