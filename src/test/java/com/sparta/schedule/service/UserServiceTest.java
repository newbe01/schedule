package com.sparta.schedule.service;

import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @InjectMocks
    BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    void userSignupTest() {
        // given
        UserSignRequest request = new UserSignRequest("testName", "testPw");
        UserService userService = new UserService(userRepository, passwordEncoder);

        // when
        userService.userSignup(request);
        given(userRepository.findByUsername("testName")).willReturn(Optional.of(new User("testName", passwordEncoder.encode("testPw"))));

        // then
        User savedUser = userRepository.findByUsername("testName").orElseThrow();
        assertThat(savedUser.getUsername()).isEqualTo("testName");
        assertThat(passwordEncoder.matches("testPw", savedUser.getPassword())).isTrue();
    }

    @DisplayName("회원가입 테스트 실패")
    @Test
    void userSignupTest2() {
        // given
        UserSignRequest request = new UserSignRequest("testName", "testPw");
        UserService userService = new UserService(userRepository, passwordEncoder);

        // when & then
        given(userRepository.findByUsername("testName")).willReturn(Optional.of(new User("testName", passwordEncoder.encode("testPw"))));
        assertThatThrownBy(() -> userService.userSignup(request)).isInstanceOf(IllegalArgumentException.class);
    }

}
