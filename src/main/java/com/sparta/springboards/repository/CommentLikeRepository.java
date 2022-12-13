package com.sparta.springboards.repository;

import com.sparta.springboards.entity.Comment;
import com.sparta.springboards.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByComment_IdAndUser_Id(Long commentid, Long userid);

    void deleteByComment_IdAndUser_Id(Long commentid, Long userid);

    int countAllByComment_Id(Long commentid);

}
