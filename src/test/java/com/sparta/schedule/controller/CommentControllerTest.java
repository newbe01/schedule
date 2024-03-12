package com.sparta.schedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.config.MockSpringSecurityFilter;
import com.sparta.schedule.config.WebSecurityConfig;
import com.sparta.schedule.domain.Comment;
import com.sparta.schedule.domain.Schedule;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.comment.CommentRequest;
import com.sparta.schedule.dto.schedule.ScheduleRequest;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(
        controllers = {CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class CommentControllerTest {

    private MockMvc mvc;
    private Principal mockPrincipal;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    WebApplicationContext context;
    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @DisplayName("댓글 생성 테스트")
    @Test
    void createCommentTest() throws Exception{
        // given
        this.mockUserSetup();

//        when(commentService.addComment(anyLong(), any(CommentRequest.class), any(User.class)))
//                .thenReturn(new Comment(commentRequest(), createSchedule(), createUser()));

        // when & then
        mvc.perform(post("/api/1/comment")
                .content(mapper.writeValueAsBytes(commentRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
        )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("댓글 생성 테스트 실패")
    @Test
    void createCommentTest_fail() throws Exception{
        // given
        this.mockUserSetup();

        when(commentService.addComment(anyLong(), any(CommentRequest.class), any(User.class)))
                .thenThrow(new IllegalArgumentException());

        // when & then
        mvc.perform(post("/api/1/comment")
                .content(mapper.writeValueAsBytes(commentRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .principal(mockPrincipal)
        )
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateCommentTest() throws Exception{
        // given
        this.mockUserSetup();

//        when(commentService.updateComment(anyLong(), anyLong(), any(CommentRequest.class), any(User.class)))
//                .thenReturn(new Comment(commentRequest(), createSchedule(), createUser()));

        // when & then
        mvc.perform(put("/api/1/comment/1")
                        .content(mapper.writeValueAsBytes(commentRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .principal(mockPrincipal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("댓글 수정 테스트 실패")
    @Test
    void updateCommentTest_fail() throws Exception{
        // given
        this.mockUserSetup();

        when(commentService.updateComment(anyLong(), anyLong(), any(CommentRequest.class), any(User.class)))
                .thenThrow(new IllegalArgumentException());

        // when & then
        mvc.perform(put("/api/1/comment/1")
                        .content(mapper.writeValueAsBytes(commentRequest()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .principal(mockPrincipal)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteCommentTest() throws Exception{
        // given
        this.mockUserSetup();

        // when & then
        mvc.perform(delete("/api/1/comment/1")
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
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }

    private User createUser() {
        return new User("test", "testPassword");
    }

    private Schedule createSchedule() {
        return new Schedule(new ScheduleRequest("test", "test"), createUser());
    }

    private CommentRequest commentRequest() {
        return new CommentRequest("content");
    }
}