package com.sparta.BoardAPI2.repository;

import com.sparta.BoardAPI2.dto.BoardListResponseDto;
import com.sparta.BoardAPI2.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<BoardListResponseDto> findAllByOrderByModifiedAtDesc();
}