package com.sparta.schedule.dto.comment;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("댓글 Dto 테스트")
class CommentDtoTest {

    @DisplayName("댓글 requestDto 생성자 테스트")
    @Test
    void newCommentRequest() {
        // given
        String content = "test comment";

        // when
        CommentRequest request = new CommentRequest(content);

        // then
        assertThat(request.getContent()).isEqualTo(content);
    }

    @DisplayName("댓글 responseDto 생성자 테스트")
    @Test
    void newCommentResponse() {
        // given
        Comment comment = createComment();

        // when
        CommentResponse response = new CommentResponse(comment);

        // then
        assertThat(response.getUsername()).isEqualTo(comment.getUser().getUsername());
        assertThat(response.getContent()).isEqualTo(comment.getContent());
    }

    private User createUser() {
        return new User("testUser", "testPw");
    }
    private Comment createComment() {
        return new Comment(
                1L,
                "test content",
                createUser(),
                new Schedule(new ScheduleRequestDto("test title", "test cont"), createUser())
        );
    }

}
