package com.sparta.schedule.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 requestDto")
public class CommentRequest {

    @Schema(description = "댓글내용")
    private String content;

}
