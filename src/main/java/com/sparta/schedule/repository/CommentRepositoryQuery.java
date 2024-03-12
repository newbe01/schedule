package com.sparta.schedule.repository;

import com.sparta.schedule.dto.comment.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryQuery {

    Page<CommentResponse> findAllComments(Long scheduleId, Pageable pageable);

}
