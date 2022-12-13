package com.sparta.springboards.service;

import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.entity.Comment;
import com.sparta.springboards.entity.CommentLike;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.repository.BoardRepository;
import com.sparta.springboards.repository.CommentLikeRepository;
import com.sparta.springboards.repository.CommentRepository;
import com.sparta.springboards.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class CommentLikeService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;


    @Transactional
    public MsgResponseDto CommentLike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        if (commentLikeRepository.findByComment_IdAndUser_Id(commentId, user.getId()).isEmpty()){
            comment.commentLikeUpDown(1);
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(commentLike);
            return new MsgResponseDto("추천!!", HttpStatus.OK.value());
        }else{
            comment.commentLikeUpDown(-1);
            commentLikeRepository.deleteByComment_IdAndUser_Id(comment.getId(), user.getId());
            return new MsgResponseDto("추천 취소", HttpStatus.OK.value());
        }
    }
}
