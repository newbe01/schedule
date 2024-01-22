package com.sparta.schedule.domain;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleUpdateDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    private String nickname;

    @Column
    private String password;

    public Schedule(ScheduleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.nickname = requestDto.getNickname();
        this.password = requestDto.getPassword();
    }

    public Schedule(String title, String content, String nickname, String password) {
        this.title = title;
        this.contents = content;
        this.nickname = nickname;
        this.password = password;
    }

    public void updateSchedule(ScheduleUpdateDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.nickname = requestDto.getNickname();
    }
}
