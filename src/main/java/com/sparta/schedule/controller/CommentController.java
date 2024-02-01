package com.sparta.schedule.controller;

import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.comment.CommentResponse;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}/comment")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Comment comment = commentService.addComment(scheduleId, request, userDetails.getUser());
        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Comment comment = commentService.updateComment(scheduleId, commentId, commentRequest, userDetails.getUser());
        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("scheduleId") Long scheduleId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(scheduleId, commentId, userDetails.getUser());
        return ResponseEntity.ok("삭제 완료");
    }

}
