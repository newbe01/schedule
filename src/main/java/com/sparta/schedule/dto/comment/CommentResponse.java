package com.sparta.schedule.dto.comment;

import com.sparta.schedule.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {

    private String content;
    private String username;

    public CommentResponse(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
    }
}
