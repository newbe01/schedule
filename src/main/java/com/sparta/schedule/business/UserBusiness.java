package com.sparta.schedule.business;

import com.sparta.schedule.domain.User;
import com.sparta.schedule.exception.NotFoundException;
import com.sparta.schedule.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserBusiness {

    private final UserRepository userRepository;

    public void findByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다");
        }
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("없는 회원입니다."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
