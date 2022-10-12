package com.sparta.BoardAPI2.service;

import com.sparta.BoardAPI2.dto.BoardRequestDto;
import com.sparta.BoardAPI2.dto.CommentRequestDto;
import com.sparta.BoardAPI2.dto.CommentResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.Comment;
import com.sparta.BoardAPI2.repository.BoardRepository;
import com.sparta.BoardAPI2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록 기본
//    @Transactional
//    public CommentResponseDto createComment(CommentRequestDto requestDto) {
//        Comment comment = new Comment(requestDto);
//        commentRepository.save(comment);
//        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
//        return commentResponseDto;
//    }

    // 댓글 등록 board 매핑
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("조회 실패"));
        Comment comment = new Comment(requestDto,board);
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    // 전체 댓글 조회
    @Transactional
    public List<CommentResponseDto> findAllComment(Long id) {
        return commentRepository.findAllByBoardId(id);
    }

    // 댓글 수정
    @Transactional
    public Long updateComment(Long boardId, Long commentId, CommentRequestDto requestDto) {
        // 어떤 게시판인지 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        // 게시판의 어떤 댓글인지 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return comment.getId();
    }

    // 댓글 삭제
    @Transactional
    public Long deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id;
    }
}
