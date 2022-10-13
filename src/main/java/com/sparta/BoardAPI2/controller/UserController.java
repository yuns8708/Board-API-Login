package com.sparta.BoardAPI2.controller;

import com.sparta.BoardAPI2.dto.*;
import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.jwt.JwtTokenProvider;
import com.sparta.BoardAPI2.repository.UserRepository;
import com.sparta.BoardAPI2.security.UserDetailsImpl;
import com.sparta.BoardAPI2.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public UserResponseDto register (@RequestBody UserRequestDto requestDto) {
        return userService.register(requestDto);
//        return requestDto;
    }

//    @GetMapping("/api/userinfo")
//    @ResponseBody
//    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        if(userDetails!=null){
//            String username = userDetails.getUser().getUsername();
//            System.out.println("로그인 된 상태입니다.");
//            return new UserInfoDto(username);
//        }
//        return new UserInfoDto();
//    }

    @GetMapping("/api/userinfo")
    @ResponseBody
    public String getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails!=null){
            System.out.println("로그인 된 상태입니다.");
            return userDetails.getUser().getUsername();
        }
        return "확인 불가";
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

    // 로그인
//    @PostMapping("/login")
//    public TokenResponse login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) throws Exception {
//        return userService.doLogin(userRequestDto,response);
//    }

}
