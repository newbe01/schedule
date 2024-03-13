package com.sparta.schedule.dto.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할일 Dto 테스트")
class ScheduleDtoTest {

    @DisplayName("할일 생성 dto 생성자 test")
    @Test
    void newScheduleRequest() {
        // given
        ScheduleRequest request = new ScheduleRequest("test title", "test contents");

        // when & then
        assertThat(request.getTitle()).isEqualTo("test title");
        assertThat(request.getContents()).isEqualTo("test contents");
    }

    @DisplayName("할일 응답 dto 생성자 test")
    @Test
    void newScheduleResponseDto() {
        // given
        ScheduleResponse response = new ScheduleResponse(createSchedule());

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
        ScheduleUpdate request = new ScheduleUpdate("update title", "update content");

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

        List<ScheduleResponse> list = user.getScheduleList().stream()
            .map(ScheduleResponse::new)
            .toList();

        ScheduleListResponse response = new ScheduleListResponse(user.getUsername(), list);

        // when & then
        assertThat(response.getSchedules()).hasSize(9);
    }

    private Schedule createSchedule() {
        return new Schedule(
            new ScheduleRequest("test title", "test content"),
            new User("testUser", "testPw")
        );
    }
}