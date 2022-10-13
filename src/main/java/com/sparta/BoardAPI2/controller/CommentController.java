package com.sparta.BoardAPI2.controller;

import com.sparta.BoardAPI2.dto.BoardRequestDto;
import com.sparta.BoardAPI2.dto.CommentRequestDto;
import com.sparta.BoardAPI2.dto.CommentResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.Comment;
import com.sparta.BoardAPI2.repository.BoardRepository;
import com.sparta.BoardAPI2.repository.CommentRepository;
import com.sparta.BoardAPI2.security.UserDetailsImpl;
import com.sparta.BoardAPI2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 댓글 등록
    @PostMapping("/boards/{id}/comments")
    public CommentResponseDto createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto, @PathVariable Long id) {
        return commentService.createComment(requestDto, id, userDetails.getUser());
    }

    // 댓글 목록 조회
    @GetMapping("/boards/{id}/comments-list")
    public List<CommentResponseDto> getAllComments(@PathVariable Long id) {
        return commentService.findAllComment(id);
    }

    // 댓글 수정
    @PutMapping("/boards/{boardId}/comments/{commentId}")
    public Long updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(userDetails.getUser(), boardId, commentId, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/boards/{boardId}/comments/{commentId}")
    public Long deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        return commentService.deleteComment(userDetails.getUser(), commentId);
    }
}
