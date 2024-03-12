package com.sparta.schedule.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sparta.schedule.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(description = "댓글 responseDto")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
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
        this.createAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
            .content(comment.getContent())
            .username(comment.getUser().getUsername())
            .createAt(comment.getCreatedAt())
            .modifiedAt(comment.getModifiedAt())
            .build();
    }
}
