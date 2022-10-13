package com.sparta.BoardAPI2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentRequestDto {

    private String content;

    private Long boardId;

    private String username;
}
