package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    public List<User> getSchedules() {
        return userRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return findOne(id);
    }

    public Schedule createSchedule(ScheduleRequestDto requestDto, User user) {

        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        Schedule schedule = new Schedule(requestDto, findUser);

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Long id, ScheduleUpdateDto requestDto, User user) {
        Schedule schedule = findOne(id);

        if (!schedule.getUser().equals(user)) {
            throw new IllegalArgumentException("다른유저");
        }

        schedule.updateSchedule(requestDto);

        return schedule;
    }

    public void deleteSchedule(Long id, ScheduleUpdateDto requestDto) {
        Schedule schedule = findOne(id);

//        isValidPassword(requestDto, schedule);

        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void completeSchedule(Long id, User user) {
        Schedule schedule = findOne(id);

        if (!schedule.getUser().equals(user)) {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateCompletion();
    }

    private Schedule findOne(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 일정입니다."));
    }
}
