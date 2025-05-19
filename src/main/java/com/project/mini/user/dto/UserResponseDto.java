package com.project.mini.user.dto;

import com.project.mini.user.entity.User;

public class UserResponseDto {
    private Long userId;
    private String email;
    private String name;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
