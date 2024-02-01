package com.sparta.schedule.repository;

import com.sparta.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByOrderByCreatAtDesc();

    List<Schedule> findAllByUser_UsernameOrderByCreatAtDesc(String username);

}
