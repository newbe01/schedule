//package com.sparta.schedule.service;
//
//import com.sparta.schedule.domain.Schedule;
//import com.sparta.schedule.dto.schedule.ScheduleRequestDto;
//import com.sparta.schedule.dto.schedule.ScheduleUpdateDto;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@Transactional
//@SpringBootTest
//class ScheduleServiceTest {
//
//    @Autowired
//    private ScheduleService service;
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Test
//    void saveTest() {
//        ScheduleRequestDto dto = new ScheduleRequestDto("title", "contents", "nickname", "password");
//
//        Schedule schedules = service.createSchedule(dto, userDetails.getUsername());
//
//        assertThat(schedules.getTitle()).isEqualTo(dto.getTitle());
//        assertThat(schedules.getContents()).isEqualTo(dto.getContents());
//        assertThat(schedules.getNickname()).isEqualTo(dto.getNickname());
//        assertThat(schedules.getPassword()).isEqualTo(dto.getPassword());
//    }
//
//    @Rollback
//    @Test
//    void findAllTest() {
//        ScheduleRequestDto dto1 = new ScheduleRequestDto("title1", "contents1", "nickname", "password");
//        ScheduleRequestDto dto2 = new ScheduleRequestDto("title2", "contents2", "nickname", "password");
//        ScheduleRequestDto dto3 = new ScheduleRequestDto("title3", "contents3", "nickname", "password");
//        ScheduleRequestDto dto4 = new ScheduleRequestDto("title4", "contents4", "nickname", "password");
//        ScheduleRequestDto dto5 = new ScheduleRequestDto("title5", "contents5", "nickname", "password");
//
//        service.createSchedule(dto1, userDetails.getUsername());
//        service.createSchedule(dto2, userDetails.getUsername());
//        service.createSchedule(dto3, userDetails.getUsername());
//        service.createSchedule(dto4, userDetails.getUsername());
//        service.createSchedule(dto5, userDetails.getUsername());
//
//        List<Schedule> schedules = service.getSchedules();
//        assertThat(schedules.get(0).getTitle()).isEqualTo(dto5.getTitle());
//        assertThat(schedules.get(0).getContents()).isEqualTo(dto5.getContents());
//    }
//
//    @Test
//    void findOne() {
//        ScheduleRequestDto dto1 = new ScheduleRequestDto("title1", "contents1", "nickname", "password");
//        ScheduleRequestDto dto2 = new ScheduleRequestDto("title2", "contents2", "nickname", "password");
//        ScheduleRequestDto dto3 = new ScheduleRequestDto("title3", "contents3", "nickname", "password");
//        ScheduleRequestDto dto4 = new ScheduleRequestDto("title4", "contents4", "nickname", "password");
//        ScheduleRequestDto dto5 = new ScheduleRequestDto("title5", "contents5", "nickname", "password");
//
//        service.createSchedule(dto1, userDetails.getUsername());
//        service.createSchedule(dto2, userDetails.getUsername());
//        service.createSchedule(dto3, userDetails.getUsername());
//        Schedule schedules = service.createSchedule(dto4, userDetails.getUsername());
//        service.createSchedule(dto5, userDetails.getUsername());
//
//        Schedule schedule = service.getSchedule(schedules.getId());
//
//        assertThat(schedule.getTitle()).isEqualTo(dto4.getTitle());
//        assertThat(schedule.getContents()).isEqualTo(dto4.getContents());
//    }
//
//    @Test
//    void update() {
//        ScheduleRequestDto dto1 = new ScheduleRequestDto("title1", "contents1", "nickname", "password");
//
//        Schedule schedule = service.createSchedule(dto1, userDetails.getUsername());
//
//        ScheduleUpdateDto updateDto = new ScheduleUpdateDto("title3", "contents3", "nick", "password");
//
//        Schedule updated = service.updateSchedule(schedule.getId(), updateDto);
//
//        assertThat(updated.getTitle()).isEqualTo(updateDto.getTitle());
//        assertThat(updated.getContents()).isEqualTo(updateDto.getContents());
//    }
//
//    @Test
//    void delete() {
//        ScheduleRequestDto dto1 = new ScheduleRequestDto("title1", "contents1", "nickname", "password");
//
//        Schedule schedule = service.createSchedule(dto1, userDetails.getUsername());
//        ScheduleUpdateDto dto = new ScheduleUpdateDto(null, null, null, "password");
//
//
//        service.deleteSchedule(schedule.getId(), dto);
//
//        assertThatThrownBy(() -> service.getSchedule(schedule.getId())).isInstanceOf(IllegalArgumentException.class);
//    }
//
//}
