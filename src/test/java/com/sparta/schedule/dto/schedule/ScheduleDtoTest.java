package com.sparta.schedule.dto.schedule;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("할일 Dto 테스트")
class ScheduleDtoTest {

    @DisplayName("할일 생성 dto 생성자 test")
    @Test
    void newScheduleRequest() {
        // given
        ScheduleRequestDto request = new ScheduleRequestDto("test title", "test contents");

        // when & then
        assertThat(request.getTitle()).isEqualTo("test title");
        assertThat(request.getContents()).isEqualTo("test contents");
    }

    @DisplayName("할일 응답 dto 생성자 test")
    @Test
    void newScheduleResponseDto() {
        // given
        ScheduleResponseDto response = new ScheduleResponseDto(createSchedule());

        // when & then
        assertThat(response.getTitle()).isEqualTo("test title");
        assertThat(response.getContents()).isEqualTo("test content");
        assertThat(response.getUsername()).isEqualTo("testUser");
        assertThat(response.isCompletionYn()).isFalse();
    }

    @DisplayName("할일 수정 dto 생성자 테스트")
    @Test
    void newScheduleUpdateDto() {
        // given
        ScheduleUpdateDto request = new ScheduleUpdateDto("update title", "update content");

        // when & then
        assertThat(request.getTitle()).isEqualTo("update title");
        assertThat(request.getContents()).isEqualTo("update content");

    }

    @DisplayName("할일 목록 생성자테스트")
    @Test
    void newScheduleListResponse() {
        // given
        User user = new User("testUser", "testPw");
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());
        user.getScheduleList().add(createSchedule());

        ScheduleListResponse response = new ScheduleListResponse(user);

        // when & then
        assertThat(response.getSchedules()).hasSize(9);
    }

    private Schedule createSchedule() {
        return new Schedule(
                new ScheduleRequestDto("test title", "test content"),
                new User("testUser", "testPw")
        );
    }
}