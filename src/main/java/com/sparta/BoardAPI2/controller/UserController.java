package com.sparta.BoardAPI2.controller;

import com.sparta.BoardAPI2.dto.LoginRequestDto;
import com.sparta.BoardAPI2.dto.UserRequestDto;
import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.jwt.JwtTokenProvider;
import com.sparta.BoardAPI2.repository.UserRepository;
import com.sparta.BoardAPI2.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;




    public UserController(UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원가입
    @PostMapping("/signup")
    public UserRequestDto register (@RequestBody UserRequestDto requestDto) {
        userService.register(requestDto);
        return requestDto;
    }

    // 로그인
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody UserRequestDto requestDto) throws Exception {
//        return ResponseEntity
//                .ok()
//                .body(userService.doLogin(requestDto));
//    }

    // 로그인
    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        User member = userRepository.findByUsername(user.get("username"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));
//        if (!user.get("password").equals(member.getPassword()) ) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return jwtTokenProvider.createToken(member.getUsername());
//    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto requestDto) {
        User member = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원입니다."));
        // 비밀번호 복호화 (passwordEncoder사용)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(member.getPassword(), requestDto.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername());
    }

}
