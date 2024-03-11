package com.sparta.schedule.service;

import com.sparta.schedule.business.ScheduleBusiness;
import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import com.sparta.schedule.exception.PermissionDeniedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleService {

    private final ScheduleBusiness scheduleBusiness;
    private final UserBusiness userBusiness;

    public Schedule createSchedule(ScheduleRequestDto requestDto, User user) {

        User findUser = userBusiness.findById(user.getId());

        Schedule schedule = new Schedule(requestDto, findUser);

        return scheduleBusiness.save(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule getSchedule(Long id) {
        return scheduleBusiness.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getSchedules() {
        return userBusiness.findAll();
    }

    public Schedule updateSchedule(Long id, ScheduleUpdateDto requestDto, User user) {
        Schedule schedule = scheduleBusiness.findById(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateSchedule(requestDto);

        return scheduleBusiness.updateSchedule(schedule);
    }

    public void completeSchedule(Long id, User user) {
        Schedule schedule = scheduleBusiness.findById(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateCompletion();
        scheduleBusiness.updateSchedule(schedule);
    }

}
