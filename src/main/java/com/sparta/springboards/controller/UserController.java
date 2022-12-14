package com.sparta.springboards.controller;

import com.sparta.springboards.dto.LoginRequestDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.dto.SignupRequestDto;
import com.sparta.springboards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//Client 요청으로부터 view 를 반환. MVC 패턴의 Controller 클래스임을 명시
@Controller

//final 또는 @NotNull 이 붙은 필드의 생성자를 자동 생성. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용
@RequiredArgsConstructor

//url 에서 공통되는 부분
@RequestMapping("/api/auth")
public class UserController {

    //HTTP request 를 받아서, Service 쪽으로 넘겨주고, 가져온 데이터들을 requestDto 파라미터로 보냄
    private final UserService userService;

    //회원가입 구현
    @PostMapping("/signup")
    //ResponseEntity: 결과값, 상태코드, 헤더값을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정해서 보내줄 수 있음 --> 구글링 필요, MsgResponseDto 의 데이터를 반환할 것임, signup 메소드 명
    //@RequestBody: HTTP Method 안의 body 값을 Mapping(key:value 로 짝지어줌), SignupRequestDto: 넘어오는 데이터를 받아주는 객체
    //@Valid: Controller 에서 유효성 검사를 할 곳에 붙임
    public ResponseEntity<MsgResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {   //(@RequestBody @Valid SignupRequestDto signupRequestDto)?
        //signupRequestDto 에 데이터를 담아서, userService 로 응답을 보냄
        userService.signup(signupRequestDto);
        //MsgResponseDto 에서 선언한 타입(여기서는 String message, int statusCode)으로 반환하는데,
        //ResponseEntity.ok(): 상태코드를 반환
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }

    //로그인 구현
    @ResponseBody
    @PostMapping("/login")
    //ResponseEntity: 결과값, 상태코드, 헤더값을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정해서 보내줄 수 있음 --> 구글링 필요, MsgResponseDto 의 데이터를 반환할 것임, login 메소드 명
    //@RequestBody: HTTP Method 안의 body 값을 Mapping(key:value 로 짝지어줌), LoginRequestDto: 넘어오는 데이터를 받아주는 객체
    //HttpServletRequest request 객체: 누가 로그인 했는지 알기위한 토큰을 담고 있음
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //loginRequestDto, response 에 데이터를 담아서, userService 로 응답을 보냄
        userService.login(loginRequestDto, response);
        //MsgResponseDto 에서 선언한 타입(여기서는 String message, int statusCode)으로 반환하는데,
        //ResponseEntity.ok(): 상태코드를 반환
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료",HttpStatus.OK.value()));
    }
}