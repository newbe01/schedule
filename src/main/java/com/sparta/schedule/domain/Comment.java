package com.sparta.schedule.domain;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

}
