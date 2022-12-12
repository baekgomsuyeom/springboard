package com.sparta.springboards.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Getter, @Setter: 필드에 선언시 자동으로 get, set 메소드 생성. 클래스에서 선언시 모든 필드에 접근자와 설정자가 자동으로 생성
@Getter

//파라미터가 없는 기본생성자를 생성
@NoArgsConstructor

//DB 테이블 역할을 함
//JPA 를 사용해 테이블과 매핑할 클래스에 붙임
//기본 생성자 필수
//name 속성: JPA 에서 사용할 엔티티 이름 지정. 따로 지정하지 않을 시 기본값으로 클래스 이름을 그대로 Entity 이름으로 지정
@Entity(name = "users")
public class User {

    //필드

    //PK(Primary Key: DB 테이블의 유일한 값)임을 지정해줌. 특정 속성을 기본키로 설정
    //@Id 어노테이션만 적게될 경우 기본키값을 직접 부여해줘야 함 --> 그래서 @GeneratedValue 를 사용
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) 또는 SEQUENCE) 또는 TABLE) 또는 AUTO)
    //각각의 기능: 기본 키 생성을 DB에 위임 (Mysql), DB 시퀀스를 사용해서 기본 키 할당 (ORACLE), 키 생성 테이블 사용 (모든 DB 사용 가능), 선택된 DB에 따라 자동으로 전략 선택
    //AUTO: DB에 따라 전략을 JPA 가 자동으로 선택되므로, DB를 변경해도 코드를 수정할 필요 없다는 장점
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    //객체 필드를 테이블 컬럼과 매핑 + 여러 속성 설정 가능
    //nullable: null 허용 여부
    //unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    //Enum 의 선언된 상수의 이름을 String 클래스 타입으로 변환하여 DB에 넣는다.(즉, DB 클래스 타입은 String 이다.) --> UserRoleEnum 에서 열거혐(enum) 으로 필드가 선언되어있음
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    //생성자
    //필드명 앞에 this. 를 붙임으로써
    //1. 객체 자기 자신을 참조한다고 밝히고,
    //2. 매개변수이름과 똑같았던 필드명을 구분지어줘서 필드에 접근 가능하도록 만듦(원래는 매개변수의 우선순위가 더 높았기때문에)
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;       //this.username: (위에서 선언된) 필드, username: 매개변수
        this.password = password;
        this.role = role;
    }
}