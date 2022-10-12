package com.sparta.BoardAPI2.dto;

import com.sparta.BoardAPI2.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentRequestDto {

    private String content;

    private Long boardId;

    public CommentRequestDto(Comment comment) {
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
    }
}
