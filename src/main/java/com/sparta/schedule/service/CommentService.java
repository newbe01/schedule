package com.sparta.schedule.service;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.exception.PermissionDeniedException;
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

        Schedule schedule = findSchedule(scheduleId);
        User findUser = findUser(user);

        return commentRepository.save(new Comment(request, schedule, findUser));
    }

    @Transactional
    public Comment updateComment(Long scheduleId, Long commentId, CommentRequest commentRequest,
        User user) {

        findSchedule(scheduleId);
        User findUser = findUser(user);
        Comment comment = findComment(commentId);

        if (!comment.getUser().equals(findUser)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        System.out.println("commentRequest = " + commentRequest.getContent());
        comment.updateComment(commentRequest);

        return comment;
    }

    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, User user) {

        findSchedule(scheduleId);
        User findUser = findUser(user);
        Comment comment = findComment(commentId);

        if (!comment.getUser().equals(findUser)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    private Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new NotFoundException("없는 일정입니다."));
    }

    private User findUser(User user) {
        return userRepository.findById(user.getId())
            .orElseThrow(() -> new NotFoundException("없는 사용자입니다."));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("없는 댓글입니다."));
    }
}
