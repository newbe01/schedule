package com.sparta.schedule.domain;

import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Schedule extends Timestamped{

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private boolean completionYn = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Comment> contentList;

    public Schedule(ScheduleRequest requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void updateSchedule(ScheduleUpdate requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void updateCompletion() {
        this.completionYn = true;
    }
}
