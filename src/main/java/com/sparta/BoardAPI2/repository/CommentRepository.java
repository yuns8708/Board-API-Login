package com.sparta.BoardAPI2.repository;

import com.sparta.BoardAPI2.dto.CommentResponseDto;
import com.sparta.BoardAPI2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResponseDto> findAllByBoardId(Long boardId);
}
