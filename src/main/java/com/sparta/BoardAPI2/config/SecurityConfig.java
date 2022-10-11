package com.sparta.BoardAPI2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 모든 경로 허용
                .antMatchers("/**").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/boards").permitAll();
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable하게 설정
                .csrf().disable()
                // h2-console 허용 설정
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}