package com.sparta.schedule.business;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentBusiness {

    private final CommentRepository commentRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("없는 댓글입니다."));
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment udpateComment(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

}
