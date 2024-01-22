package com.sparta.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


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
    private String content;

    @Column
    private String nickname;

    @Column
    private String password;

}
