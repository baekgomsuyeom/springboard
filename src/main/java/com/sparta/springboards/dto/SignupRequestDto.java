package com.sparta.springboards.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {

    //필드
    //문자열, 배열등의 크기 설정
    @Size(min = 4,max = 10,message ="아이디는 4에서 10자 사이 입니다.")
    //정규식 설정
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디 형식이 일치하지 않습니다.")
    private String username;

    @Size(min = 8,max = 15,message ="비밀번호는 8에서 15자 사이 입니다.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$",message = "비밀번호 형식이 일치하지 않습니다.")
    private String password;

    //private String email;
    private boolean admin = false;  //admin 인지 아닌지 확인
    private String adminToken = "";
}