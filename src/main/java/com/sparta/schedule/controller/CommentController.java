package com.sparta.schedule.controller;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CommentResponse> addComment(
            @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
            @PathVariable("scheduleId") Long scheduleId,

            @Parameter(description = "댓글 requestDto")
            @RequestBody CommentRequest request,

            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.addComment(scheduleId, request, userDetails.getUser());

        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.OK);
    }

    @Operation(summary = "update comment", description = "댓글 수정", responses = {
            @ApiResponse(responseCode = "200", description = "successful"),
            @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @PutMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
            @PathVariable("scheduleId") Long scheduleId,

            @Parameter(description = "댓글의 번호", in = ParameterIn.PATH)
            @PathVariable("commentId") Long commentId,

            @Parameter(description = "댓글 requestDto")
            @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Comment comment = commentService.updateComment(scheduleId, commentId, commentRequest, userDetails.getUser());
        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.OK);
    }

    @Operation(summary = "delete comment", description = "댓글 삭제", responses = {
            @ApiResponse(responseCode = "200", description = "successful"),
            @ApiResponse(responseCode = "405", description = "Invalid")
    })
    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(
            @Parameter(description = "할일의 번호", in = ParameterIn.PATH)
            @PathVariable("scheduleId") Long scheduleId,

            @Parameter(description = "댓글의 번호", in = ParameterIn.PATH)
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(scheduleId, commentId, userDetails.getUser());
        return ResponseEntity.ok("삭제 완료");
    }

}
