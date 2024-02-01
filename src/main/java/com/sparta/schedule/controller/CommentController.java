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
import org.springframework.security.core.userdetails.UserDetails;
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

        Comment comment = commentService.addComment(scheduleId, request, userDetails.getUsername());
        return new ResponseEntity<>(new CommentResponse(comment), HttpStatus.OK);
    }

}
