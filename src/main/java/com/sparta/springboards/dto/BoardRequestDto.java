package com.sparta.springboards.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter, @Setter: 필드에 선언시 자동으로 get, set 메소드 생성. 클래스에서 선언시 모든 필드에 접근자와 설정자가 자동으로 생성
@Getter
@Setter

//파라미터가 없는 기본생성자를 생성
@NoArgsConstructor
@AllArgsConstructor

public class BoardRequestDto {

    //필드    --> 사용자(Client)가 직접 입력하는 부분
    private String title;
    private String contents;
}