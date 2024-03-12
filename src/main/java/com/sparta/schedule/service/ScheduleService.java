package com.sparta.schedule.service;

import com.sparta.schedule.business.ScheduleBusiness;
import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import com.sparta.schedule.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleService {

    private final ScheduleBusiness scheduleBusiness;
    private final UserBusiness userBusiness;

    public ScheduleResponse createSchedule(ScheduleRequest requestDto, User user) {

        User findUser = userBusiness.findById(user.getId());

        Schedule schedule = new Schedule(requestDto, findUser);
        Schedule saveSchedule = scheduleBusiness.save(schedule);

        return ScheduleResponse.of(saveSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long id) {
        Schedule schedule = scheduleBusiness.findById(id);
        return ScheduleResponse.of(schedule);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleListResponse> getSchedules(String titleCond, Pageable pageable) {
        return scheduleBusiness.getAllSchedules(titleCond, pageable);
    }

    public ScheduleResponse updateSchedule(Long id, ScheduleUpdate requestDto, User user) {
        Schedule schedule = scheduleBusiness.findById(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateSchedule(requestDto);
        Schedule updateSchedule = scheduleBusiness.updateSchedule(schedule);
        return ScheduleResponse.of(updateSchedule);
    }

    public ScheduleResponse completeSchedule(Long id, User user) {
        Schedule schedule = scheduleBusiness.findById(id);

        if (!schedule.getUser().equals(user)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        schedule.updateCompletion();
        Schedule updateSchedule = scheduleBusiness.updateSchedule(schedule);
        return ScheduleResponse.of(updateSchedule);
    }

}
