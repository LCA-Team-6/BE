package com.project.mini.user.service;

import com.project.mini.auth.dto.SignUpDto;
import com.project.mini.user.entity.User;
import com.project.mini.user.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.mini.user.dto.UserResponseDto;
import com.project.mini.user.dto.UserUpdateDto;
import com.project.mini.user.dto.ChangePasswordDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor

class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto getCurrentUser() {
        User user = getCurrentAuthenticatedUser();
        return new UserResponseDto(user);
    }

    public void updateUserInfo(UserUpdateDto updateDto) {
        User user = getCurrentAuthenticatedUser();
        user.setName(updateDto.getName());
        user.setEmail(updateDto.getEmail());
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordDto dto) {
        User user = getCurrentAuthenticatedUser();
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteCurrentUser() {
        User user = getCurrentAuthenticatedUser();
        userRepository.delete(user);
    }

    private User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}