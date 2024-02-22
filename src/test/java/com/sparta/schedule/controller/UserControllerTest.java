package com.sparta.schedule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.config.MockSpringSecurityFilter;
import com.sparta.schedule.config.WebSecurityConfig;
import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
            @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = WebSecurityConfig.class
            )
        }
)
class UserControllerTest {

    private MockMvc mvc;
    @Autowired ObjectMapper mapper;
    @Autowired WebApplicationContext context;
    @MockBean UserService userService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @DisplayName("회원가입 테스트")
    @Test
    void userSignupTest() throws Exception{
        // given
        UserSignRequest userSignRequest = new UserSignRequest("test", "testPassword");
        String request = mapper.writeValueAsString(userSignRequest);

        // when & then
        mvc.perform(post("/api/users/signup")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원가입 테스트 실패1")
    @ParameterizedTest
    @ValueSource(strings = {"testtesttest", "test!!!!!", "테스트테스트테"})
    void userSignupTest_fail1(String username) throws Exception{
        // given
        UserSignRequest userSignRequest = new UserSignRequest(username, "testPassword");
        String request = mapper.writeValueAsString(userSignRequest);

        // when & then
        mvc.perform(post("/api/users/signup")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원가입 테스트 실패2")
    @ParameterizedTest
    @ValueSource(strings = {"test", "test!!!!!", "테스트테스트테"})
    void userSignupTest_fail2(String password) throws Exception{
        // given
        UserSignRequest userSignRequest = new UserSignRequest("test", password);
        String request = mapper.writeValueAsString(userSignRequest);

        // when & then
        mvc.perform(post("/api/users/signup")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}
