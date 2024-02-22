package com.sparta.schedule.domain;

import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("댓글 테스트")
class CommentTest {

    @DisplayName("생성자 테스트")
    @Test
    void newCommentTest() {
        // given
        User user = new User("test", "test");
        Schedule schedule = new Schedule(new ScheduleRequestDto("title", "content"), user);
        CommentRequest request = new CommentRequest("test");

        // when
        Comment comment = new Comment(request, schedule, user);

        // then
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getSchedule()).isEqualTo(schedule);
        assertThat(comment.getContent()).isEqualTo(request.getContent());
    }

    @DisplayName("수정 테스트")
    @Test
    void updateCommentTest() {
        // given
        User user = new User("test", "test");
        Schedule schedule = new Schedule(new ScheduleRequestDto("title", "content"), user);
        Comment comment = new Comment(new CommentRequest("test"), schedule, user);

        CommentRequest request = new CommentRequest("update");
        // when
        comment.updateComment(request);

        // then
        assertThat(comment.getContent()).isEqualTo(request.getContent());
    }
}