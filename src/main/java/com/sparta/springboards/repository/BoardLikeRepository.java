package com.sparta.springboards.repository;

import com.sparta.springboards.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    void deleteByBoardIdAndUserId(Long boardId, Long userId);

    boolean existsByBoardIdAndUserId(Long boardId, Long userId);
}