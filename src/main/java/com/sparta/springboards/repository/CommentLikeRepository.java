package com.sparta.springboards.repository;

import com.sparta.springboards.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentid, Long userid);
    void deleteByCommentIdAndUserId(Long commentid, Long userid);
    int countAllByCommentId(Long commentid);
}
