package com.sparta.schedule.repository;

import com.sparta.schedule.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @DisplayName("저장 테스트")
    @Test
    void saveTest() {
        // given
        User user = new User("testUser", "testPw");

        // when
        User savedUser = userRepository.save(user);

        //then
        assertThat(user).isEqualTo(savedUser);
    }

    @DisplayName("조회 테스트")
    @Test
    void findTest() {
        // given
        User user = new User("testUser", "testPw");
        User savedUser = userRepository.save(user);

        // when
        User findUser = userRepository.findById(savedUser.getId()).orElse(null);

        // then
        assertThat(findUser).isEqualTo(savedUser);
    }

    @DisplayName("삭제 테스트")
    @Test
    void deleteTest() {
        // given
        User user = new User("testUser", "testPw");
        User savedUser = userRepository.save(user);

        // when
        userRepository.delete(savedUser);

        // then
        assertThatThrownBy(() -> userRepository.findById(savedUser.getId()).orElseThrow(() -> new IllegalArgumentException("")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원명으로 회원 찾기")
    @Test
    void findByUsername1() {
        // given
        User user = new User("testUser", "testPw");
        userRepository.save(user);
        String username = "testUser";

        // when
        User findUser = userRepository.findByUsername(username).get();

        // then
        assertThat(findUser).isEqualTo(user);
    }

    @DisplayName("회원명으로 없는회원 찾기")
    @Test
    void findByUsername2() {
        // given
        User user = new User("testUser", "testPw");
        userRepository.save(user);
        String username = "testUser2";

        // when & then
        assertThatThrownBy(() -> userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("")))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
