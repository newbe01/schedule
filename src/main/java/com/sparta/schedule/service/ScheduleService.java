package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.exception.PermissionDeniedException;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Schedule createSchedule(ScheduleRequestDto requestDto, User user) {

        User findUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new NotFoundException("없는 회원입니다."));

        Schedule schedule = new Schedule(requestDto, findUser);

        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(Long id) {
        return findOne(id);
    }

    public List<User> getSchedules() {
        return userRepository.findAll();
    }

    @Transactional
    public Schedule updateSchedule(Long id, ScheduleUpdateDto requestDto, User user) {
        Schedule schedule = findOne(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateSchedule(requestDto);

        return schedule;
    }

    @Transactional
    public void completeSchedule(Long id, User user) {
        Schedule schedule = findOne(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateCompletion();
    }

    private Schedule findOne(Long id) {
        return scheduleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("없는 일정입니다."));
    }
}
