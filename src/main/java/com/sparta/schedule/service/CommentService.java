package com.sparta.schedule.service;

import com.sparta.schedule.business.CommentBusiness;
import com.sparta.schedule.business.ScheduleBusiness;
import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.exception.PermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentBusiness commentBusiness;
    private final ScheduleBusiness scheduleBusiness;
    private final UserBusiness userBusiness;

    public Comment addComment(Long scheduleId, CommentRequest request, User user) {

        Schedule schedule = scheduleBusiness.findById(scheduleId);
        User findUser = userBusiness.findById(user.getId());

        return commentBusiness.save(new Comment(request, schedule, findUser));
    }

    public Comment updateComment(Long scheduleId, Long commentId, CommentRequest commentRequest,
        User user) {

        scheduleBusiness.findById(scheduleId);
        User findUser = userBusiness.findById(user.getId());
        Comment comment = commentBusiness.findById(commentId);

        if (!comment.getUser().equals(findUser)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        comment.updateComment(commentRequest);
        return commentBusiness.udpateComment(comment);
    }

    public void deleteComment(Long scheduleId, Long commentId, User user) {

        scheduleBusiness.findById(scheduleId);
        User findUser = userBusiness.findById(user.getId());
        Comment comment = commentBusiness.findById(commentId);

        if (!comment.getUser().equals(findUser)) {
            throw new PermissionDeniedException("작성자만 삭제/수정할 수 있습니다.");
        }

        commentBusiness.deleteComment(comment);
    }

}
