package com.sparta.schedule.domain;

import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("일정 테스트")
class ScheduleTest {

    @DisplayName("생성자 테스트")
    @Test
    void newScheduleTest() {

        // given
        User user = new User("test", "test");
        ScheduleRequestDto requestDto = new ScheduleRequestDto("title", "content");
        Schedule schedule = new Schedule(requestDto, user);

        // when & then
        assertThat(schedule.getUser()).isEqualTo(user);
        assertThat(schedule.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(schedule.getContents()).isEqualTo(requestDto.getContents());
    }

    @DisplayName("수정 테스트")
    @Test
    void updateScheduleTest() {

        // given
        User user = new User("test", "test");
        ScheduleRequestDto requestDto = new ScheduleRequestDto("title", "content");
        Schedule schedule = new Schedule(requestDto, user);

        ScheduleUpdateDto updateDto = new ScheduleUpdateDto("update title", "update contents");
        schedule.updateSchedule(updateDto);

        // when & then
        assertThat(schedule.getUser()).isEqualTo(user);
        assertThat(schedule.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(schedule.getContents()).isEqualTo(updateDto.getContents());
    }

    @DisplayName("할일 완료 테스트")
    @Test
    void completionYn() {
        // given
        User user = new User("test", "test");
        ScheduleRequestDto requestDto = new ScheduleRequestDto("title", "content");
        Schedule schedule = new Schedule(requestDto, user);
        schedule.updateCompletion();

        // when & then
        assertThat(schedule.isCompletionYn()).isTrue();
    }
}