package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.sparta.schedule.business.CommentBusiness;
import com.sparta.schedule.business.ScheduleBusiness;
import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentBusiness commentBusiness;
    @Mock
    ScheduleBusiness scheduleBusiness;
    @Mock
    UserBusiness userBusiness;
    @InjectMocks
    CommentService commentService;

    @DisplayName("댓글 생성 테스트")
    @Test
    void addCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        CommentRequest request = new CommentRequest("test comment");

        given(scheduleBusiness.findById(anyLong())).willReturn(new Schedule());
        given(userBusiness.findById(any())).willReturn(new User());
        given(commentBusiness.save(any())).willReturn(new Comment(request, new Schedule(), user));

        // when
        CommentResponse result = commentService.addComment(1L, request, user);

        // then
        then(commentBusiness).should().save(any(Comment.class));
        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getContent()).isEqualTo(request.getContent());
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        Schedule schedule = new Schedule();
        Comment comment = new Comment(new CommentRequest("test comment"), schedule, user);

        CommentRequest request = new CommentRequest("update comment");

        given(scheduleBusiness.findById(anyLong())).willReturn(new Schedule());
        given(userBusiness.findById(any())).willReturn(user);
        given(commentBusiness.findById(anyLong())).willReturn(comment);
        given(commentBusiness.udpateComment(any(Comment.class))).willReturn(new Comment(request, schedule, user));

        // when
        CommentResponse response = commentService.updateComment(1L, 1L, request, user);

        // then
        then(commentBusiness).should().findById(any());
        assertThat(response.getContent()).isEqualTo(request.getContent());

    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteCommentTest() {
        // given
        User user = new User("testUser", "testPw");
        Schedule schedule = new Schedule();
        Comment comment = new Comment(new CommentRequest("test comment"), schedule, user);

        given(scheduleBusiness.findById(anyLong())).willReturn(new Schedule());
        given(userBusiness.findById(any())).willReturn(user);
        given(commentBusiness.findById(anyLong())).willReturn(comment);

        // when
        commentService.deleteComment(1L, 1L, user);

        // then
        then(commentBusiness).should().deleteComment(any(Comment.class));
    }
}
