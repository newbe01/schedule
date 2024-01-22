package com.sparta.schedule.service;

import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleRepository repository;

    @Test
    void createSchedule() {
        Schedule schedule = new Schedule("title", "contents", "nickname", "pw");

        Schedule savedOne = repository.save(schedule);

        assertThat(savedOne).isEqualTo(schedule);
    }

    @Test
    void findAll() {
        Schedule schedule1 = new Schedule("title1", "contents1", "nickname1", "pw1");
        Schedule schedule2 = new Schedule("title2", "contents2", "nickname2", "pw2");
        Schedule schedule3 = new Schedule("title3", "contents3", "nickname3", "pw3");
        Schedule schedule4 = new Schedule("title4", "contents4", "nickname4", "pw4");
        Schedule schedule5 = new Schedule("title5", "contents5", "nickname5", "pw5");
        Schedule schedule6 = new Schedule("title6", "contents6", "nickname6", "pw6");

        repository.save(schedule1);
        repository.save(schedule2);
        repository.save(schedule3);
        repository.save(schedule4);
        repository.save(schedule5);
        repository.save(schedule6);

        List<Schedule> findAll = repository.findAll();

        assertThat(findAll.size()).isEqualTo(6);
    }
}