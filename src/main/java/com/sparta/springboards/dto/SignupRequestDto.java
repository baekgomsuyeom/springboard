package com.sparta.springboards.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {

    //필드
    private String username;
    private String password;
    private String email;
    private boolean admin = false;  //admin 인지 아닌지 확인
    private String adminToken = "";
}