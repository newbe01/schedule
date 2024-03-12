package com.sparta.schedule.controller;

import com.sparta.schedule.common.CommonResponse;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "comment", description = "댓글 API")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "add comment", description = "댓글 추가", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PostMapping("/{scheduleId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<CommentResponse> addComment(
        @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId,

        @Parameter(description = "댓글 requestDto")
        @RequestBody CommentRequest request,

        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponse response = commentService.addComment(scheduleId, request,
            userDetails.getUser());

        return CommonResponse.<CommentResponse>builder()
            .data(response)
            .build();
    }

    @Operation(summary = "update comment", description = "댓글 수정", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("/{scheduleId}/comment/{commentId}")
    public CommonResponse<CommentResponse> updateComment(
        @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId,

        @Parameter(description = "댓글의 번호", in = ParameterIn.PATH)
        @PathVariable("commentId") Long commentId,

        @Parameter(description = "댓글 requestDto")
        @RequestBody CommentRequest commentRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        CommentResponse response = commentService.updateComment(scheduleId, commentId,
            commentRequest,
            userDetails.getUser());

        return CommonResponse.<CommentResponse>builder()
            .data(response)
            .build();
    }

    @Operation(summary = "delete comment", description = "댓글 삭제", responses = {
        @ApiResponse(responseCode = "200", description = "successful"),
        @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<Void> deleteComment(
        @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
        @PathVariable("scheduleId") Long scheduleId,

        @Parameter(description = "댓글의 번호", in = ParameterIn.PATH)
        @PathVariable("commentId") Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(scheduleId, commentId, userDetails.getUser());

        return CommonResponse.<Void>builder()
            .message("삭제 완료")
            .build();
    }

}
