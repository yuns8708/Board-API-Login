package com.sparta.BoardAPI2.service;

import com.sparta.BoardAPI2.dto.UserRequestDto;
import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.dto.UserResponseDto;
import com.sparta.BoardAPI2.jwt.JwtTokenProvider;
import com.sparta.BoardAPI2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    @Resource(name="userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    // 회원가입 조건 체크
    public boolean checkSignupValueCondition(UserRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        boolean checkValueCondition = true;
        String pattern = "^[a-zA-Z0-9]*$";
        if( !(Pattern.matches(pattern,username) && username.length()>=4 && username.length()<=12) ){
            System.out.println("닉네임이 잘못되었습니다");
            checkValueCondition=false;
        }
        else if( !(Pattern.matches(pattern,password) && password.length()>=4 && password.length()<=32) ){
            System.out.println("비밀번호가 잘못되었습니다");
            checkValueCondition=false;
        }
        else if( !password.equals(passwordCheck) ){
            System.out.println("비밀번호 확인과 일치하지 않습니다");
            checkValueCondition=false;
        }
        return checkValueCondition;
    }

    // 회원가입
    public UserResponseDto register(UserRequestDto requestDto) {
        // 요구조건 확인
        if(!checkSignupValueCondition(requestDto)){
            throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
        };

        //회원 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword())); ;
        User user = new User(requestDto);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

//    public TokenResponse register(UserRequestDto requestDto) {
//        // 요구조건 확인
//        if(!checkSignupValueCondition(requestDto)){
//            throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
//        };
//
//        //회원 닉네임 중복 확인
//        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
//        if(found.isPresent()){
//            throw new IllegalArgumentException("중복된 사용자 id가 존재합니다.");
//        }
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword())); ;
//        User user = new User(requestDto);
//        userRepository.save(user);
//        String accessToken = tokenProvider.createAccessToken(user.getUserId());
//        String refreshToken = tokenProvider.createRefreshToken(user.getUserId());
//        Auth auth = Auth.builder()
//                .user(user)
//                .refreshToken(refreshToken)
//                .build();
//        authRepository.save(auth);
//        //토큰들을 반환한 순간 로그인 처리가 된 것임
//        TokenResponse token = TokenResponse.builder()
//                .ACCESS_TOKEN(accessToken)
//                .REFRESH_TOKEN(refreshToken)
//                .build();
////        return tokenProvider.builder()
////                .ACCESS_TOKEN(accessToken)
////                .REFRESH_TOKEN(refreshToken)
////                .build();
//        return token;
//    }

}
