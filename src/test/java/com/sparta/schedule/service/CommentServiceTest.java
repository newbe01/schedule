package com.sparta.schedule.service;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    ScheduleRepository scheduleRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    CommentService commentService;

    @DisplayName("댓글 생성 테스트")
    @Test
    void addCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        CommentRequest request = new CommentRequest("test comment");

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(new Schedule()));
        given(userRepository.findById(any())).willReturn(Optional.of(new User()));
        given(commentRepository.save(any())).willReturn(new Comment(request, new Schedule(), user));

        // when
        Comment savedComment = commentService.addComment(1L, request, user);

        // then
        then(commentRepository).should().save(any(Comment.class));
        assertThat(savedComment.getUser()).isEqualTo(user);
        assertThat(savedComment.getContent()).isEqualTo(request.getContent());

    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        Schedule schedule = new Schedule();
        Comment comment = new Comment(new CommentRequest("test comment"), schedule, user);

        CommentRequest request = new CommentRequest("update comment");

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(new Schedule()));
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // when
        Comment updateComment = commentService.updateComment(1L, 1L, request, user);

        // then
        then(commentRepository).should().findById(any());
        assertThat(updateComment.getContent()).isEqualTo(request.getContent());

    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        Schedule schedule = new Schedule();
        Comment comment = new Comment(new CommentRequest("test comment"), schedule, user);

        given(scheduleRepository.findById(anyLong())).willReturn(Optional.of(new Schedule()));
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment));

        // when
        commentService.deleteComment(1L, 1L, user);

        // then
        then(commentRepository).should().delete(any(Comment.class));
    }
}