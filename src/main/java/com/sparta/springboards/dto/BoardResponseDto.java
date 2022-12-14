package com.sparta.springboards.dto;

import com.sparta.springboards.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Getter, @Setter: 필드에 선언시 자동으로 get, set 메소드 생성. 클래스에서 선언시 모든 필드에 접근자와 설정자가 자동으로 생성
@Getter

//파라미터가 없는 기본생성자를 생성
@NoArgsConstructor

public class BoardResponseDto {

    //필드
    private Long id;
    private String title;
    private String contents;
    private String username;
    private int boardLikeCount;
    private boolean boardLikeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //생성자
    public BoardResponseDto(Board board) {      //매개변수를 가지는 생성자

        this.id = board.getId();            //this.id: (위에서 선언된) 필드, Board 객체의 board 매개변수로 들어온 데이터를 getId() 에 담는다(Client 에게로 보내기 위해)
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();

    }

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public BoardResponseDto(Board board, List<CommentResponseDto> commentList, boolean boardLikeCheck) {
        this.id = board.getId();            //this.id: (위에서 선언된) 필드, Board 객체의 board 매개변수로 들어온 데이터를 getId() 에 담는다(Client 에게로 보내기 위해)
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.username = board.getUsername();
        this.boardLikeCount = board.getBoardLikeList().size();
        this.boardLikeCheck = boardLikeCheck;
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = commentList;
    }
}