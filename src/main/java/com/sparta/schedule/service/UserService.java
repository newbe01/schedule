package com.sparta.schedule.service;

import com.sparta.schedule.business.UserBusiness;
import com.sparta.schedule.domain.User;
import com.sparta.schedule.dto.user.UserResponse;
import com.sparta.schedule.dto.user.UserSignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserBusiness userBusiness;
    private final PasswordEncoder passwordEncoder;

    public UserResponse userSignup(UserSignRequest request) {

        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());

        userBusiness.findByUsername(request.getUsername());

        User saveUser = userBusiness.saveUser(new User(username, password));

        return UserResponse.of(saveUser);
    }

}
