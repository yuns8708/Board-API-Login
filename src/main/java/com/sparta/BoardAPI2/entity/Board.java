package com.sparta.BoardAPI2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.BoardAPI2.dto.BoardRequestDto;
import com.sparta.BoardAPI2.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Board extends Timestamped {
    // 글 고유 아이디
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "boardId")
    private Long id;

    // 글 제목
    @Column(nullable = false)
    private String title;

    // 글 내용
    @Column(nullable = false)
    private String content;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 작성자
//    @Column(nullable = false)
//    private String username;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    List<Comment> comments = new ArrayList<>();


    // requestDto 정보를 가져와서 entity 만들 때 사용
//    public Board(BoardRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
//    }
//
//    public Board(User user, BoardRequestDto requestDto) {
//        this.user = user;
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
//    }

//    public Board(UserDetailsImpl userDetails, BoardRequestDto requestDto) {
//        this.username = userDetails.getUsername();
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
//    }

// requestDto 정보를 가져와서 entity 만들 때 사용
public Board(BoardRequestDto requestDto) {
    this.title = requestDto.getTitle();
    this.content = requestDto.getContent();
    this.password = requestDto.getPassword();
}

    // 생성 (user매핑)
//    public Board( User user, BoardRequestDto requestDto) {
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//        this.password = requestDto.getPassword();
//        this.user = user;
//    }

    // 업데이트 메소드
    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }
}
