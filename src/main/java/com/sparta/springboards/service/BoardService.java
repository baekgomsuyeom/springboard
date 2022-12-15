package com.sparta.springboards.service;

import com.sparta.springboards.dto.BoardRequestDto;
import com.sparta.springboards.dto.BoardResponseDto;
import com.sparta.springboards.dto.CommentResponseDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.entity.*;
import com.sparta.springboards.exception.CustomException;
import com.sparta.springboards.repository.BoardLikeRepository;
import com.sparta.springboards.repository.BoardRepository;
import com.sparta.springboards.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.springboards.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    //게시글 작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        if(requestDto.getCategory().equals("TIL") || requestDto.getCategory().equals("Spring") || requestDto.getCategory().equals("Java")){
            Board board = boardRepository.save(new Board(requestDto, user));
            return new BoardResponseDto(board);
        }else{
            throw new CustomException(NOT_EXIST_CATEGORY);
        }
    }


    //전체 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getListBoards(User user, String category) {
        if(category.equals("TIL") || category.equals("Spring") || category.equals("Java")){
            List<Board> boardList =boardRepository.findAllByCategoryOrderByCreatedAtDesc(category);
            List<BoardResponseDto> boardResponseDto = new ArrayList<>();

            for (Board board : boardList) {
                List<CommentResponseDto> commentList = new ArrayList<>();
                for (Comment comment : board.getComments()) {
                    commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
                }
                boardResponseDto.add(new BoardResponseDto(
                        board,
                        commentList,
                        (checkBoardLike(board.getId(), user))));
            }
            return boardResponseDto;
        } else {
            List<Board> boardList =  boardRepository.findAllByOrderByCreatedAtDesc();
            List<BoardResponseDto> boardResponseDto = new ArrayList<>();
            for (Board board : boardList) {
                List<CommentResponseDto> commentList = new ArrayList<>();
                for (Comment comment : board.getComments()) {
                    commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
                }
                boardResponseDto.add(new BoardResponseDto(
                        board,
                        commentList,
                        (checkBoardLike(board.getId(), user))));
            }
            return boardResponseDto;
        }
    }

    //선택한 게시글 조회
    @Transactional(readOnly = true)
    public BoardResponseDto getBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentResponseDto(comment,commentLikeRepository.countAllByCommentId(comment.getId())));
        }

        return new BoardResponseDto(
                board,
                commentList,
                (checkBoardLike(board.getId(), user)));
    }

    //선택한 게시글 수정(변경)
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto requestDto, User user) {
        Board board;
        if(user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BOARD)
            );
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        board.update(requestDto);
        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentResponseDto(comment, commentLikeRepository.countAllByCommentId(comment.getId())));
        }
        if(requestDto.getCategory().equals("TIL") || requestDto.getCategory().equals("Spring") || requestDto.getCategory().equals("Java")){
            return new BoardResponseDto(
                    board,
                    commentList,
                    (checkBoardLike(board.getId(), user)));
        }else{
            throw new CustomException(NOT_EXIST_CATEGORY);
        }
    }

    //선택한 게시글 삭제
    @Transactional
    public void deleteBoard (Long id, User user) {
        Board board;
        if(user.getRole().equals(UserRoleEnum.ADMIN)) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BOARD)
            );
        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        boardRepository.delete(board);
    }
    @Transactional(readOnly = true)
    public boolean checkBoardLike(Long boardId, User user) {
        Optional<BoardLike> boardLike = boardLikeRepository.findByBoardIdAndUserId(boardId, user.getId());
        return boardLike.isPresent();
    }
    @Transactional
    public MsgResponseDto saveBoardLike(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        if (!checkBoardLike(boardId, user)) {
            boardLikeRepository.saveAndFlush(new BoardLike(board, user));
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            boardLikeRepository.deleteByBoardIdAndUserId(boardId, user.getId());
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}