package com.sparta.schedule.domain;

import com.sparta.schedule.dto.comment.CommentRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(CommentRequest request, Schedule schedule, User user) {
        this.content = request.getContent();
        this.schedule = schedule;
        this.user = user;
    }
}
