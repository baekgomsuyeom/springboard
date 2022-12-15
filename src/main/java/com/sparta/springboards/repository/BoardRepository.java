package com.sparta.springboards.repository;

import com.sparta.springboards.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();
    List<Board> findAllByCategoryOrderByCreatedAtDesc(String category);
    Optional<Board> findByIdAndUserId(Long id, Long userId);
}
