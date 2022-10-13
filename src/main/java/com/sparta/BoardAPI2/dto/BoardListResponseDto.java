package com.sparta.BoardAPI2.dto;

import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardListResponseDto {
    // 제목
    private String title;

    // 작성자명
    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    // Entity -> dto

    public BoardListResponseDto(Board board) {
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.createdAt = board.getModifiedAt();
        this.modifiedAt = board.getCreatedAt();
    }
}