package com.project.mini.user.service;

import com.project.mini.auth.dto.SignUpDto;
import com.project.mini.user.entity.User;
import com.project.mini.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public void signup(SignUpDto signUpDto) {
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        User user = User.builder()
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
    }


}
