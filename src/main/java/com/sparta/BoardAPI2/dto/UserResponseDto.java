package com.sparta.BoardAPI2.dto;
import com.sparta.BoardAPI2.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String username;

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }
}