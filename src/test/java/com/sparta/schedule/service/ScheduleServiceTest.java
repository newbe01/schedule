package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sparta.schedule.business.ScheduleBusiness;
import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleBusiness scheduleBusiness;
    @Mock
    UserBusiness userBusiness;

    @InjectMocks
    ScheduleService scheduleService;

    @DisplayName("할일 생성 테스트")
    @Test
    void createSchedule() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");
        given(userBusiness.findById(any())).willReturn(
            new User("testUser", "testPw"));
        given(scheduleBusiness.save(any(Schedule.class))).willReturn(new Schedule(request, user));

        // when
        ScheduleResponse schedule = scheduleService.createSchedule(request, user);

        // then
        then(userBusiness).should().findById(user.getId());
        then(scheduleBusiness).should().save(any(Schedule.class));
        assertThat(schedule.getUsername()).isEqualTo(user.getUsername());
        assertThat(schedule.getTitle()).isEqualTo(request.getTitle());
        assertThat(schedule.getContents()).isEqualTo(request.getContents());
    }


    @DisplayName("할일 단건조회 테스트")
    @Test
    void findOneTest() {
        // given
        Long id = 1L;
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");

        given(scheduleBusiness.findById(anyLong())).willReturn(
            new Schedule(request, user));

        // when
        ScheduleResponse schedule = scheduleService.getSchedule(id);

        // then
        then(scheduleBusiness).should().findById(anyLong());
        assertThat(schedule.getUsername()).isEqualTo(user.getUsername());
        assertThat(schedule.getTitle()).isEqualTo(request.getTitle());
        assertThat(schedule.getContents()).isEqualTo(request.getContents());
    }

    @DisplayName("할일 리스트조회 테스트")
    @Test
    void findAllTest() {
        // given
        User user = new User();
        List<ScheduleResponse> list = new ArrayList<>();
        ScheduleListResponse response = new ScheduleListResponse(user.getUsername(), list);
        given(scheduleBusiness.getAllSchedules(any(), any())).willReturn(
            new PageImpl<>(List.of(response, response, response)));

        // when
        Page<ScheduleListResponse> schedules = scheduleService.getSchedules(any(), any());

        // then
        then(scheduleBusiness).should().getAllSchedules(any(), any());
        assertThat(schedules.getSize()).isEqualTo(3);
    }

    @DisplayName("할일 수정 테스트")
    @Test
    void updateTest() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");

        Schedule schedule = new Schedule(request, user);

        ScheduleUpdate updateRequest = new ScheduleUpdate("update", "update");

        given(scheduleBusiness.findById(anyLong())).willReturn(schedule);
        given(scheduleBusiness.updateSchedule(any())).willReturn(
            new Schedule(new ScheduleRequest("update", "update"), user));

        // when
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(1L, updateRequest, user);

        // then
        then(scheduleBusiness).should().findById(anyLong());
        assertThat(scheduleResponse.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(scheduleResponse.getContents()).isEqualTo(updateRequest.getContents());
    }

    @DisplayName("할일 완료 테스트")
    @Test
    void completionTest() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");
        Schedule schedule = new Schedule(request, user);

        given(scheduleBusiness.findById(anyLong())).willReturn(schedule);

        given(scheduleBusiness.updateSchedule(any())).willReturn(
            new Schedule(1L, "title", "content", true, user, new ArrayList<>()));
        // when
        scheduleService.completeSchedule(1L, user);

        // then
        then(scheduleBusiness).should().findById(anyLong());
        assertThat(schedule.isCompletionYn()).isTrue();
    }

}
