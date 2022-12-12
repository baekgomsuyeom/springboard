package com.sparta.springboards.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class LoginRequestDto {
    //필드
    private String username;
    private String password;
}