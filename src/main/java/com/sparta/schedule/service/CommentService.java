package com.sparta.schedule.service;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment addComment(Long scheduleId, CommentRequest request, User user) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("없는 일정"));
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없는 유저"));

        return commentRepository.save(new Comment(request, schedule, findUser));
    }

    @Transactional
    public Comment updateComment(Long scheduleId, Long commentId, CommentRequest commentRequest, User user) {

        scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("없는 일정"));
        User findUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없는 유저"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("없는 댓글"));

        if (!comment.getUser().equals(findUser)) {
            throw new IllegalArgumentException("다른 회원");
        }

        System.out.println("commentRequest = " + commentRequest.getContent());
        comment.updateComment(commentRequest);

        return comment;
    }
}
