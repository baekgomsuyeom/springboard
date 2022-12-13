package com.sparta.springboards.service;

import com.sparta.springboards.dto.CommentRequestDto;
import com.sparta.springboards.dto.CommentResponseDto;
import com.sparta.springboards.entity.Board;
import com.sparta.springboards.entity.Comment;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.entity.UserRoleEnum;
import com.sparta.springboards.repository.BoardRepository;
import com.sparta.springboards.repository.CommentLikeRepository;
import com.sparta.springboards.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.save(new Comment(commentRequestDto, board, user));
        int cnt = 0;
        return new CommentResponseDto(comment,cnt);
    }

    @Transactional
    public CommentResponseDto updateComment(Long boardId, Long cmtId, CommentRequestDto commentRequestDto, User user) {

        // 게시글의 DB 저장 유무 확인
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

        } else {
            //user 의 권한이 ADMIN 이 아니라면, 아이디가 같은 유저만 수정 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }

        comment.update(commentRequestDto);

        return new CommentResponseDto(comment ,commentLikeRepository.countAllByComment_Id(comment.getId()));
    }

    @Transactional
    public CommentResponseDto deleteComment(Long boardId, Long cmtId, User user) {

        // 게시글의 DB 저장 유무 확인
        Board board = boardRepository.findById(boardId).orElseThrow (
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

        } else {
            //user 의 권한이 ADMIN 이 아니라면, 아이디가 같은 유저만 수정 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }

        // 해당 댓글 삭제
        commentRepository.deleteById(cmtId);

        return new CommentResponseDto(comment,commentLikeRepository.countAllByComment_Id(comment.getId()));
    }
}
