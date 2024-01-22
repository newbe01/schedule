package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository repository;


    public List<ScheduleResponseDto> getSchedules() {
        return repository.findAll().stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }
}
