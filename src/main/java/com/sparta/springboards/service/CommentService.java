package com.sparta.springboards.service;

import com.sparta.springboards.dto.CommentRequestDto;
import com.sparta.springboards.dto.CommentResponseDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.entity.*;
import com.sparta.springboards.entity.Board;
import com.sparta.springboards.entity.Comment;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.entity.UserRoleEnum;
import com.sparta.springboards.exception.CustomException;
import com.sparta.springboards.repository.BoardRepository;
import com.sparta.springboards.repository.CommentLikeRepository;
import com.sparta.springboards.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.sparta.springboards.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;

    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        Comment comment = commentRepository.save(new Comment(commentRequestDto, board, user));
        int cnt = 0;
        return new CommentResponseDto(comment,cnt);
    }

    //댓글 수정(변경)
    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long cmtId, CommentRequestDto commentRequestDto, User user) {
        //DB 에 게시글 저장 확인
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );
        } else {
            //user 의 권한이 ADMIN 이 아니라면, 아이디가 같은 유저만 수정 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment ,commentLikeRepository.countAllByCommentId(comment.getId()));
    }

    //댓글 삭제
    @Transactional
    public CommentResponseDto deleteComment(Long boardId, Long cmtId, User user) {
        //DB 에 게시글 저장 확인
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );
        } else {
            //user 의 권한이 ADMIN 이 아니라면, 아이디가 같은 유저만 수정 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        //해당 댓글 삭제
        commentRepository.deleteById(cmtId);
        return new CommentResponseDto(comment,commentLikeRepository.countAllByCommentId(comment.getId()));
    }

    //댓글 좋아요
    @Transactional
    public MsgResponseDto CommentLike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );
        if (commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId()).isEmpty()){
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(commentLike);
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        }else {
            commentLikeRepository.deleteByCommentIdAndUserId(comment.getId(), user.getId());
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
