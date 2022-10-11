package com.sparta.BoardAPI2.controller;

import com.sparta.BoardAPI2.dto.UserRequestDto;
import com.sparta.BoardAPI2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public UserRequestDto register (@RequestBody UserRequestDto requestDto) {
        userService.register(requestDto);
        return requestDto;
    }
}
