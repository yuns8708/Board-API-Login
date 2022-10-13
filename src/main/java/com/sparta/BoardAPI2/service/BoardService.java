package com.sparta.BoardAPI2.service;

import com.sparta.BoardAPI2.dto.BoardListResponseDto;
import com.sparta.BoardAPI2.dto.BoardRequestDto;
import com.sparta.BoardAPI2.dto.BoardResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import com.sparta.BoardAPI2.entity.User;
import com.sparta.BoardAPI2.repository.BoardRepository;
import com.sparta.BoardAPI2.repository.UserRepository;
import com.sparta.BoardAPI2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 글 생성 (user 매핑)
//    @Transactional
    public BoardResponseDto createBoard(User user, BoardRequestDto requestDto) {
        Board board = new Board(user, requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(user, board);
    }

//    public BoardResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, BoardRequestDto requestDto) {
//        Board board = new Board(userDetails, requestDto);
//        boardRepository.save(board);
//        return new BoardResponseDto(board);
//    }
// 글 생성
//public BoardResponseDto createBoard(BoardRequestDto requestDto) {
//    Board board = new Board(requestDto);
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    boardRepository.save(board);
//    return new BoardResponseDto(board);
//}

//    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
//        Board board = new Board(requestDto);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        requestDto.setUsername(authentication.getName());
//        boardRepository.save(board);
//        return new BoardResponseDto(board);
//    }

    // 모든 글 가져오기
//    public List<BoardResponseDto> findAllBoard() {
//        try{
//            List<Board> boardList = boardRepository.findAll();
//
//            List<BoardResponseDto> responseDtoList = new ArrayList<>();
//
//            for (Board board : boardList) {
//                responseDtoList.add(
//                        new BoardResponseDto(board)
//                );
//            }
//            return responseDtoList;
//        } catch (Exception e) {
//            throw new NullPointerException("찾을 수 없는 글입니다.");
//        }
//    }

    public List<BoardListResponseDto> findAllBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    // 글 하나 가져오기
    public BoardResponseDto findOneBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 없습니다")
        );
        return new BoardResponseDto(board);
    }

    // 글 수정
    @Transactional
    public Long updateBoard(User user, Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (user.getUsername().equals(board.getUser().getUsername())) {
            board.update(requestDto);
            return board.getId();
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
    }

    // 삭제
    @Transactional
    public Long deleteBoard(User user, Long id) {
        // 어떤 게시판인지 찾기
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );

        if (user.getUsername().equals(board.getUser().getUsername())) {
            boardRepository.deleteById(id);
            return id;
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
    }

    // 비밀번호 일치 확인
    public boolean checkPassword(Long id, String inputPassword) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다.")
        );
        if (inputPassword.equals(board.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
