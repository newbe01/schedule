package com.sparta.schedule.dto.comment;

import com.sparta.schedule.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema( description = "댓글 responseDto")
public class CommentResponse {

    @Schema(description = "댓글")
    private String content;

    @Schema(description = "작성자")
    private String username;

    @Schema(description = "생성일")
    private LocalDateTime createAt;

    @Schema(description = "수정일")
    private LocalDateTime modifiedAt;


    public CommentResponse(Comment comment) {
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createAt = comment.getCreatAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
