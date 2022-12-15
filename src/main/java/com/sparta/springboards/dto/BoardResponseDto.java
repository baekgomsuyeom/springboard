package com.sparta.springboards.dto;

import com.sparta.springboards.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;
    private String category;
    private String contents;
    private String username;
    private int boardLikeCount;
    private boolean boardLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    private List<CommentResponseDto> commentList = new ArrayList<>();
    public BoardResponseDto(Board board, List<CommentResponseDto> commentList, boolean boardLikeCheck) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.category = board.getCategory();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.boardLikeCount = board.getBoardLikeList().size();
        this.boardLikeCheck = boardLikeCheck;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = commentList;
    }
}