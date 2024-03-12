package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock ScheduleRepository scheduleRepository;
    @Mock UserRepository userRepository;
    @InjectMocks ScheduleService scheduleService;

    @DisplayName("할일 생성 테스트")
    @Test
    void createSchedule() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");
        given(userRepository.findById(any())).willReturn(Optional.of(new User("testUser", "testPw")));
        given(scheduleRepository.save(any(Schedule.class))).willReturn(new Schedule(request, user));

        // when
//        Schedule schedule = scheduleService.createSchedule(request, user);

        // then
        then(userRepository).should().findById(user.getId());
        then(scheduleRepository).should().save(any(Schedule.class));
//        assertThat(schedule.getUser()).isEqualTo(user);
//        assertThat(schedule.getTitle()).isEqualTo(request.getTitle());
//        assertThat(schedule.getContents()).isEqualTo(request.getContents());
    }


    @DisplayName("할일 단건조회 테스트")
    @Test
    void findOneTest() {
        // given
        Long id =1L;
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(new Schedule(request, user)));

        // when
//        Schedule schedule = scheduleService.getSchedule(id);

        // then
        then(scheduleRepository).should().findById(anyLong());
//        assertThat(schedule.getUser()).isEqualTo(user);
//        assertThat(schedule.getTitle()).isEqualTo(request.getTitle());
//        assertThat(schedule.getContents()).isEqualTo(request.getContents());
    }

    @DisplayName("할일 리스트조회 테스트")
    @Test
    void findAllTest() {
        // given
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        given(userRepository.findAll()).willReturn(List.of(user1, user2, user3));

        // when
        List<User> schedules = scheduleService.getSchedules();

        // then
        then(userRepository).should().findAll();
        assertThat(schedules.size()).isEqualTo(3);
    }

    @DisplayName("할일 수정 테스트")
    @Test
    void updateTest() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");

        Schedule schedule = new Schedule(request, user);

        ScheduleUpdate updateRequest = new ScheduleUpdate("update", "update");

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(schedule));

        // when
//        Schedule updateSchedule = scheduleService.updateSchedule(1L, updateRequest, user);

        // then
        then(scheduleRepository).should().findById(anyLong());
//        assertThat(updateSchedule.getTitle()).isEqualTo(updateRequest.getTitle());
//        assertThat(updateSchedule.getContents()).isEqualTo(updateRequest.getContents());
    }

    @DisplayName("할일 완료 테스트")
    @Test
    void completionTest() {
        // given
        User user = new User("testUser", "testPw");
        ScheduleRequest request = new ScheduleRequest("title", "content");
        Schedule schedule = new Schedule(request, user);

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(schedule));

        // when
        scheduleService.completeSchedule(1L, user);

        // then
        then(scheduleRepository).should().findById(anyLong());
        assertThat(schedule.isCompletionYn()).isTrue();
    }

}
