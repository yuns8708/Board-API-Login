package com.sparta.BoardAPI2.dto;

import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardResponseDto {

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String username;

    // board의 정보를 받아 boardResponseDto 생성
    public BoardResponseDto(User user, Board board) {
        this.username = user.getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
    }

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
        this.username = board.getUser().getUsername();
    }
}