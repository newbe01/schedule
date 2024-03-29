package com.sparta.schedule.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.config.MockSpringSecurityFilter;
import com.sparta.schedule.config.WebSecurityConfig;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.schedule.ScheduleListResponse;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.dto.schedule.ScheduleResponse;
import com.sparta.schedule.dto.schedule.ScheduleUpdate;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.s3.S3UploadService;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.ScheduleService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {ScheduleController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class ScheduleControllerTest {

    private MockMvc mvc;
    private Principal mockPrincipal;

    @MockBean
    S3UploadService s3UploadService;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    WebApplicationContext context;
    @MockBean
    ScheduleService scheduleService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    @DisplayName("할일 생성 테스트")
    @Test
    void createScheduleTest() throws Exception {
        // given
        ScheduleRequest requestDto = requestDto();
        String request = mapper.writeValueAsString(requestDto);
        this.mockUserSetup();

        when(scheduleService.createSchedule(any(ScheduleRequest.class), any(User.class)))
            .thenReturn(ScheduleResponse.of(new Schedule(requestDto, createUser())));

        // when & then
        mvc.perform(post("/api/schedules")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("할일 생성 테스트 실패")
    @Test
    void createScheduleTest_fail() throws Exception {
        // given
        ScheduleRequest requestDto = requestDto();
        String request = mapper.writeValueAsString(requestDto);
        this.mockUserSetup();

        when(s3UploadService.saveFile(anyString(), any())).thenReturn("");
        when(scheduleService.createSchedule(any(), any(User.class)))
            .thenThrow(new NotFoundException("없는 회원입니다."));

        // when & then
        mvc.perform(post("/api/schedules")
                .content(request)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("할일 조회 테스트")
    @Test
    void getScheduleTest() throws Exception {
        // given
        when(scheduleService.getSchedule(anyLong()))
            .thenReturn(ScheduleResponse.of(new Schedule(requestDto(), createUser())));

        // when & then
        mvc.perform(get("/api/schedules/1")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("할일 조회 테스트 실패")
    @Test
    void getScheduleTest_fail() throws Exception {
        // given
        when(scheduleService.getSchedule(anyLong()))
            .thenThrow(new IllegalArgumentException("없는 일정입니다."));

        // when & then
        mvc.perform(get("/api/schedules/1")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("할일 목록 조회 테스트")
    @Test
    void getScheduleListTest() throws Exception {
        // given
        User user = createUser();
        List<ScheduleResponse> list = new ArrayList<>();
        ScheduleListResponse response = new ScheduleListResponse(user.getUsername(), list);
        when(scheduleService.getSchedules(anyString(), any()))
            .thenReturn(new PageImpl<>(List.of(response, response, response)));

        // when & then
        mvc.perform(get("/api/schedules")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("할일 수정 테스트")
    @Test
    void updateScheduleTest() throws Exception {
        // given
        ScheduleUpdate updateDto = new ScheduleUpdate("update", "update");
        this.mockUserSetup();

        when(scheduleService.updateSchedule(anyLong(), any(ScheduleUpdate.class), any(User.class)))
            .thenReturn(ScheduleResponse.of(
                new Schedule(
                    new ScheduleRequest(updateDto.getTitle(), updateDto.getContents()),
                    createUser()
                ))
            );

        // when & then
        mvc.perform(put("/api/schedules/1")
                .content(mapper.writeValueAsBytes(updateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("할일 수정 테스트 실패")
    @Test
    void updateScheduleTest_fail() throws Exception {
        // given
        ScheduleUpdate updateDto = new ScheduleUpdate("update", "update");
        this.mockUserSetup();

        when(scheduleService.updateSchedule(anyLong(), any(ScheduleUpdate.class), any(User.class)))
            .thenThrow(new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다."));

        // when & then
        mvc.perform(put("/api/schedules/1")
                .content(mapper.writeValueAsBytes(updateDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
            )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("할일 완료 테스트")
    @Test
    void completeScheduleTest() throws Exception {
        // given
        this.mockUserSetup();

        // when & then
        mvc.perform(put("/api/schedules/1/complete")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        String username = "test";
        String password = "testPassword";
        User testUser = new User(username, password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());
    }

    private User createUser() {
        return new User("test", "testPassword");
    }

    private ScheduleRequest requestDto() {
        return new ScheduleRequest("test title", "test content");
    }

}