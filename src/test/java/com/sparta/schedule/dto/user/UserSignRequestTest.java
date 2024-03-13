package com.sparta.schedule.dto.user;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("회원 Dto 테스트")
class UserSignRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @DisplayName("생성자 테스트")
    @Test
    void newUserSignRequest() {
        // given
        UserSignRequest request = new UserSignRequest("testUser", "testPassword");

        // when & then
        assertThat(request.getUsername()).isEqualTo("testUser");
        assertThat(request.getPassword()).isEqualTo("testPassword");

    }

    @DisplayName("이름 validation")
    @ParameterizedTest
    @ValueSource(strings = {"t", "test!!!!", "테스트테스테"})
    public void validateName() {
        // given
        UserSignRequest request = new UserSignRequest("a", "testPassword");

        // when
        Set<ConstraintViolation<UserSignRequest>> validate = validator.validate(request);

        // then
        assertThat(validate).extracting("message").contains("영소문자와 숫자만, 4~10길이만 허용");
    }

    @DisplayName("비밀번호 validation")
    @ParameterizedTest
    @ValueSource(strings = {"test", "test!!!!!", "테스트테스트테"})
    public void validatePassword(String password) {
        // given
        UserSignRequest request = new UserSignRequest("testUser", password);

        // when
        Set<ConstraintViolation<UserSignRequest>> validate = validator.validate(request);

        // then
        assertThat(validate).extracting("message").contains("영대소문자와 숫자만, 8~15길이만 허용");
    }

}
