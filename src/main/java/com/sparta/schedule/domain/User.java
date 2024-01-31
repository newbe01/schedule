package com.sparta.schedule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    private String password;

}
