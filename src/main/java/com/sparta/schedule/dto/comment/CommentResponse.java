package com.sparta.schedule.dto.comment;

import com.sparta.schedule.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private String content;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public CommentResponse(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createAt = comment.getCreatAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
