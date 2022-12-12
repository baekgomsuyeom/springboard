package com.sparta.springboards.entity;

import com.sparta.springboards.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Getter, @Setter: 필드에 선언시 자동으로 get, set 메소드 생성. 클래스에서 선언시 모든 필드에 접근자와 설정자가 자동으로 생성
@Getter
@Setter
@Entity
@Table(name = "Board")

//파라미터가 없는 기본생성자를 생성
@NoArgsConstructor

public class Board extends Timestamped {

    //PK(Primary Key: DB 테이블의 유일한 값)임을 지정해줌. 특정 속성을 기본키로 설정
    //@Id 어노테이션만 적게될 경우 기본키값을 직접 부여해줘야 함 --> 그래서 @GeneratedValue 를 사용
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) 또는 SEQUENCE) 또는 TABLE) 또는 AUTO)
    //각각의 기능: 기본 키 생성을 DB에 위임 (Mysql), DB 시퀀스를 사용해서 기본 키 할당 (ORACLE), 키 생성 테이블 사용 (모든 DB 사용 가능), 선택된 DB에 따라 자동으로 전략 선택
    //AUTO: DB에 따라 전략을 JPA 가 자동으로 선택되므로, DB를 변경해도 코드를 수정할 필요 없다는 장점
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    //객체 필드를 테이블 컬럼과 매핑 + 여러 속성 설정 가능
    //nullable: null 허용 여부
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    //생성자
    //게시글 작성
    public Board(BoardRequestDto requestDto, User user) {   //userId -> user
        this.title = requestDto.getTitle();             //this.title: (위에서 선언된) 필드, BoardRequestDto 객체의 requestDto 매개변수로 들어온 데이터를 getTitle() 에 담는다(DB 로 보내기 위해)
        this.username = user.getUsername();
        this.contents = requestDto.getContents();
        this.user = user;          //userId -> user          //userId: 다른 값과 일치하는지를 비교해서 본인 인증을 위해 (연관관계를 짓기 위함)?
    }

    //선택한 게시글 수정(변경)
    public void update(BoardRequestDto boardrequestDto) {       //boardrequestDto? requestDto?
        this.title = boardrequestDto.getTitle();             //this.title: (위에서 선언된) 필드, BoardRequestDto 객체의 requestDto 매개변수로 들어온 데이터를 getTitle() 에 담는다(DB 로 보내기 위해)
        this.contents = boardrequestDto.getContents();

    }
}