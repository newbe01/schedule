package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.user.UserSignRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserBusiness userBusiness;

    @InjectMocks
    BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    void userSignupTest() {
        // given
        UserSignRequest request = new UserSignRequest("testName", "testPw");
        UserService userService = new UserService(userBusiness, passwordEncoder);
        given(userBusiness.saveUser(any())).willReturn(
            new User("testName", passwordEncoder.encode("testPw")));

        // when
        userService.userSignup(request);
        given(userBusiness.findById(anyLong())).willReturn(
            new User("testName", passwordEncoder.encode("testPw")));

        // then
        User savedUser = userBusiness.findById(1L);
        assertThat(savedUser.getUsername()).isEqualTo("testName");
        assertThat(passwordEncoder.matches("testPw", savedUser.getPassword())).isTrue();
    }

    @DisplayName("회원가입 테스트 실패")
    @Test
    void userSignupTest2() {
        // given
        UserSignRequest request = new UserSignRequest("testName", "testPw");
        UserService userService = new UserService(userBusiness, passwordEncoder);
        doThrow(new IllegalArgumentException()).when(userBusiness).findByUsername(any());

        // when & then
        assertThatThrownBy(() -> userService.userSignup(request)).isInstanceOf(
            IllegalArgumentException.class);
    }

}
