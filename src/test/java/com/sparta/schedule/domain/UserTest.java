package com.sparta.schedule.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("회원 테스트")
class UserTest {

    @DisplayName("생성자 테스트")
    @Test
    void newUserTest() {
        // given
        User user = new User("test", "test");

        // when & then
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("test");
    }

}
