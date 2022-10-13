package com.sparta.BoardAPI2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoardRequestDto {

    private String title;

    private String content;

    private String password;
//
//    private String username;
}