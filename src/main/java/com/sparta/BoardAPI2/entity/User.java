package com.sparta.BoardAPI2.entity;

import com.sparta.BoardAPI2.dto.UserRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity //db테이블과 일대일로 매핑
@Table(name = "users") // 테이블명 지정
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }
}
