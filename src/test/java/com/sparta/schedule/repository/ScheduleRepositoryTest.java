package com.sparta.schedule.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given
        User user = createUser();
        userRepository.save(user);
        ScheduleRequest request = new ScheduleRequest("test title", "test content");

        Schedule schedule = new Schedule(request, user);

        // when
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // then
        assertThat(savedSchedule.getUser()).isEqualTo(user);
        assertThat(savedSchedule).isEqualTo(schedule);

    }

    @DisplayName("조회 테스트")
    @Test
    void findTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule(user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // when
        Schedule findSchedule = scheduleRepository.findById(savedSchedule.getId()).orElseThrow();

        // then
        assertThat(findSchedule).isEqualTo(savedSchedule);
    }

    @DisplayName("전체조회 테스트")
    @Test
    void findAllTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule(user);
        Schedule schedule2 = createSchedule(user);
        Schedule schedule3 = createSchedule(user);
        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule2);
        scheduleRepository.save(schedule3);

        // when
        Page<ScheduleListResponse> allSchedules = scheduleRepository.getAllSchedules(null,
            Pageable.ofSize(3));

        // then
        assertThat(allSchedules.getSize()).isEqualTo(3);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule(user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // when
        scheduleRepository.delete(schedule);

        // then
        assertThatThrownBy(() -> scheduleRepository.findById(savedSchedule.getId())
            .orElseThrow(() -> new IllegalArgumentException("")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private Schedule createSchedule(User user) {
        ScheduleRequest request = new ScheduleRequest("test title", "test content");
        return new Schedule(request, user);
    }

    private User createUser() {
        User user = new User("testUser", "testPw");
        return userRepository.save(user);
    }
}