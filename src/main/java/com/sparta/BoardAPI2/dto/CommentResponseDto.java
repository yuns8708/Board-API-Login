package com.sparta.BoardAPI2.dto;

import com.sparta.BoardAPI2.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private Long boardId;

    private String content;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
    }
}
