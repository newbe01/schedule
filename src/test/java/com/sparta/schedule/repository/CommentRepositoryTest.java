package com.sparta.schedule.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
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
class CommentRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule();

        CommentRequest request = new CommentRequest("test content");
        Comment comment = new Comment(request, schedule, user);

        // when
        Comment savedComment = commentRepository.save(comment);

        // then
        assertThat(savedComment.getUser()).isEqualTo(user);
        assertThat(savedComment.getSchedule()).isEqualTo(schedule);
        assertThat(savedComment.getContent()).isEqualTo("test content");
    }

    @DisplayName("조회 테스트")
    @Test
    void findTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule();

        Comment comment = new Comment(new CommentRequest("test content"), schedule, user);
        Comment savedComment = commentRepository.save(comment);

        // when
        Comment findComment = commentRepository.findById(savedComment.getId()).orElseThrow();

        // then
        assertThat(findComment.getUser()).isEqualTo(user);
        assertThat(findComment.getSchedule()).isEqualTo(schedule);
        assertThat(findComment.getContent()).isEqualTo("test content");
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void getAllCommentTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule();

        Comment comment = new Comment(new CommentRequest("test content1"), schedule, user);
        Comment comment2 = new Comment(new CommentRequest("test content2"), schedule, user);
        Comment comment3 = new Comment(new CommentRequest("test content3"), schedule, user);
        Comment comment4 = new Comment(new CommentRequest("test content4"), schedule, user);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

        // when
        Page<CommentResponse> result = commentRepository.findAllComments(schedule.getId(),
            Pageable.ofSize(4));

        // then
        assertThat(result.getSize()).isEqualTo(4);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        User user = createUser();
        Schedule schedule = createSchedule();

        Comment comment = new Comment(new CommentRequest("test content"), schedule, user);
        Comment savedComment = commentRepository.save(comment);

        // when
        commentRepository.delete(savedComment);

        // then
        assertThatThrownBy(() -> commentRepository.findById(savedComment.getId())
            .orElseThrow(() -> new IllegalArgumentException("")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private Schedule createSchedule() {
        // given
        User user = new User("testUser", "testPw");
        userRepository.save(user);
        ScheduleRequest request = new ScheduleRequest("test title", "test content");

        Schedule schedule = new Schedule(request, user);

        // when
        return scheduleRepository.save(schedule);
    }

    private User createUser() {
        User user = new User("testUser2", "testPw");
        return userRepository.save(user);
    }

}