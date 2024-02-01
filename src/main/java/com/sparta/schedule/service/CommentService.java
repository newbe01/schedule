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
    public Comment addComment(Long scheduleId, CommentRequest request, String username) {

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("없는 일정"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 유저"));


        return commentRepository.save(new Comment(request, schedule, user));
    }
}
