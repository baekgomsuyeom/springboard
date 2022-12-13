package com.sparta.springboards.service;

import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.entity.Board;
import com.sparta.springboards.entity.BoardLike;
import com.sparta.springboards.entity.User;
import com.sparta.springboards.exception.CustomException;
import com.sparta.springboards.repository.BoardLikeRepository;
import com.sparta.springboards.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.springboards.exception.ErrorCode.NOT_FOUND_BOARD;
import static com.sparta.springboards.exception.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public boolean checkBoradLike(Long boardId, User user) {
        // 해당 회원의 좋아요 여부 확인
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, user.getId());
        return boardLike.isEmpty();
    }

    @Transactional
    public MsgResponseDto saveBoradLike(Long boardId, User user) {
        // 입력 받은 게시글 id와 일치하는 DB 조회
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 해당 회원의 좋아요 여부를 확인하고 비어있으면 좋아요, 아니면 좋아요 취소
        if (checkBoradLike(boardId, user)) {
            boardLikeRepository.saveAndFlush(new BoardLike(board, user));
            board.updateLikeCount(1);
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            boardLikeRepository.deleteByBoardIdAndUserId(boardId, user.getId());
            board.updateLikeCount(-1);
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
