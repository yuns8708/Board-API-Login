package com.sparta.BoardAPI2.service;

import com.sparta.BoardAPI2.dto.CommentRequestDto;
import com.sparta.BoardAPI2.dto.CommentResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.Comment;
import com.sparta.BoardAPI2.entity.User;
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

    // 댓글 등록 board 매핑
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("조회 실패"));
        Comment comment = new Comment(requestDto, board, user);
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
    public Long updateComment(User user, Long boardId, Long commentId, CommentRequestDto requestDto) {
        // 어떤 게시판인지 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        // 게시판의 어떤 댓글인지 찾기
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        // 댓글의 username과 로그인 유저의 username 비교
        if (user.getUsername().equals(comment.getUser().getUsername())) {
            comment.update(requestDto);
            return comment.getId();
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
    }

    // 댓글 삭제
    @Transactional
    public Long deleteComment(User user, Long id) {
        // 어떤 댓글인지 찾기
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        // 댓글의 username과 로그인 유저의 username 비교
        if (user.getUsername().equals(comment.getUser().getUsername())) {
            commentRepository.deleteById(id);
            return id;
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
    }
}
