package com.sparta.BoardAPI2.service;

import com.sparta.BoardAPI2.dto.TokenResponse;
import com.sparta.BoardAPI2.dto.UserRequestDto;
import com.sparta.BoardAPI2.entity.Auth;
import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final TokenProvider tokenProvider;
//    private final AuthRepository authRepository;
//    private final PasswordEncoder passwordEncoder;

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
    public UserRequestDto register(UserRequestDto requestDto) {
        // 요구조건 확인
        if(!checkSignupValueCondition(requestDto)){
            throw new IllegalArgumentException("회원가입 정보가 정확하지 않습니다.");
        };

        //회원 닉네임 중복 확인
        Optional<User> found = userRepository.findByUsername(requestDto.getUsername());
        if(found.isPresent()){
            throw new IllegalArgumentException("중복된 사용자 id가 존재합니다.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword())); ;
        User user = new User(requestDto);
        userRepository.save(user);
        return requestDto;
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

    // 로그인
//    @Transactional
//    public TokenResponse doLogin(UserRequestDto requestDto) throws Exception {
//        User user = userRepository.findByUsername(requestDto.getUsername())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
//        Auth auth = authRepository.findByUserId(user.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
//        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
//            throw new Exception("비밀번호가 일치하지 않습니다.");
//        }
//
//        String accessToken = "";
//        String refreshToken = auth.getRefreshToken();   //DB에서 가져온 Refresh 토큰
//
//        //refresh 토큰은 유효 할 경우
//        //사실 이것도 로그인을 하는것이라면 refresh 토큰이 유효한지 파악안하고 그냥 둘다 재발급 하는게 나을듯?
//        if (tokenProvider.isValidRefreshToken(refreshToken)) {
//            accessToken = tokenProvider.createAccessToken(user.getUserId()); //Access Token 새로 만들어서 줌
//            return TokenResponse.builder()
//                    .ACCESS_TOKEN(accessToken)
//                    .REFRESH_TOKEN(refreshToken)
//                    .build();
//        } else {
//            //둘 다 새로 발급
//            accessToken = tokenProvider.createAccessToken(user.getUserId());
//            refreshToken = tokenProvider.createRefreshToken(user.getUserId());
//            auth.refreshUpdate(refreshToken);   //DB Refresh 토큰 갱신
//        }
//
//        return TokenResponse.builder()
//                .ACCESS_TOKEN(accessToken)
//                .REFRESH_TOKEN(refreshToken)
//                .build();
//    }

}
