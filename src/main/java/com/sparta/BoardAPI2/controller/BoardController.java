package com.sparta.BoardAPI2.controller;

import com.sparta.BoardAPI2.dto.BoardListResponseDto;
import com.sparta.BoardAPI2.dto.BoardRequestDto;
import com.sparta.BoardAPI2.dto.BoardResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.security.UserDetailsImpl;
import com.sparta.BoardAPI2.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 등록
//    @PostMapping("/boards")
//    public BoardResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardRequestDto requestDto){
//
//        return boardService.createBoard(userDetails, requestDto);
//    }
    // 글 등록
    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto){
        BoardResponseDto board = boardService.createBoard(requestDto);
        return board;
    }

    // 전체 목록 조회
    @GetMapping("/boards-list")
    public List<BoardListResponseDto> getAllBoards() {
        return boardService.findAllBoard();
    }

    // 글 하나 조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto getOneBoard(@PathVariable Long id) {
        return boardService.findOneBoard(id);
    }

    // 글 수정
    @PutMapping("/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id,requestDto);
    }

    // 글 삭제
    @DeleteMapping("/boards/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        return  boardService.deleteBoard(id);
    }

    // 비밀번호 확인
    @GetMapping("/boards/check/{id}/{inputPassword}")
    public boolean checkPassword(@PathVariable Long id,@PathVariable String inputPassword) {
        return boardService.checkPassword(id, inputPassword);
    }
}