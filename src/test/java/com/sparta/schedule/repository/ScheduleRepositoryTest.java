package com.sparta.schedule.repository;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@DataJpaTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given
        User user = new User("testUser", "testPw");
        userRepository.save(user);
        ScheduleRequestDto request = new ScheduleRequestDto("test title", "test content");

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
        Schedule schedule = createSchedule();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // when
        Schedule findSchedule = scheduleRepository.findById(savedSchedule.getId()).orElseThrow();

        // then
        assertThat(findSchedule).isEqualTo(savedSchedule);
    }


    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        Schedule schedule = createSchedule();
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // when
        scheduleRepository.delete(schedule);

        // then
        assertThatThrownBy(() -> scheduleRepository.findById(savedSchedule.getId()).orElseThrow(() -> new IllegalArgumentException("")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Schedule createSchedule() {
        User user = new User("testUser", "testPw");
        userRepository.save(user);
        ScheduleRequestDto request = new ScheduleRequestDto("test title", "test content");
        return new Schedule(request, user);
    }
}