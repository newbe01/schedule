package com.sparta.schedule.service;

import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.user.UserSignRequest;
import com.sparta.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void userSignup(UserSignRequest request) {

        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        Optional<User> findUser = userRepository.findByUsername(request.getUsername());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        userRepository.save(new User(username, password));
    }

}
