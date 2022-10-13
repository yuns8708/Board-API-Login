package com.sparta.BoardAPI2.security;

import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =  userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다"));
        return new UserDetailsImpl(user);

    }
}