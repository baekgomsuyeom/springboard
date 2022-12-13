package com.sparta.springboards.service;

import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.entity.Comment;
import com.sparta.springboards.entity.CommentLike;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.exception.CustomException;
import com.sparta.springboards.repository.BoardRepository;
import com.sparta.springboards.repository.CommentLikeRepository;
import com.sparta.springboards.repository.CommentRepository;
import com.sparta.springboards.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.springboards.exception.ErrorCode.NOT_FOUND_COMMENT;
import static com.sparta.springboards.exception.ErrorCode.NOT_FOUND_USER;

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
                () -> new CustomException(NOT_FOUND_COMMENT)
        );

        if (commentLikeRepository.findByComment_IdAndUser_Id(commentId, user.getId()).isEmpty()){
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(commentLike);
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        }else{
            commentLikeRepository.deleteByComment_IdAndUser_Id(comment.getId(), user.getId());
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
