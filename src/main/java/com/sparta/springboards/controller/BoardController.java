package com.sparta.springboards.controller;

import com.sparta.springboards.dto.BoardRequestDto;
import com.sparta.springboards.dto.BoardResponseDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.security.UserDetailsImpl;
import com.sparta.springboards.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller 클래스(여기서는 BoardController)의 각 하위 메서드에 @ResponseBody 를 붙이지 않아도(@Controller 와 달리), 문자열과 JSON 등을 전송 가능
//RestController 의 주용도는 Json 형태로 객체 데이터를 반환하는 것
//@Controller + @ResponseBody 를 결합한 어노테이션
@RestController

//final 또는 @NotNull 이 붙은 필드의 생성자를 자동 생성. 주로 의존성 주입(Dependency Injection) 편의성을 위해서 사용
@RequiredArgsConstructor

//url 에서 공통되는 부분
@RequestMapping("/api")

public class BoardController {

    //HTTP request 를 받아서, Service 쪽으로 넘겨주고, 가져온 데이터들을 requestDto 파라미터로 보냄
    private final BoardService boardService;    //boardService 를 가져다 쓰겠다 (의존성 주입)

    //게시글 작성
    @PostMapping("/post")
    //BoardResponseDto 반환 타입, createBoard 메소드 명
    //@RequestBody: HTTP Method 안의 body 값을 Mapping(key:value 로 짝지어줌), BoardRequestDto: 넘어오는 데이터를 받아주는 객체
    //@AuthenticationPrincipal: 인증 객체(Authentication)의 Principal 부분의 값을 가져온다
    //UserDetailsImpl userDetails: 인증 객체를 만들 때, Principal 부분에 userDetails 를 넣었기 때문에, userDetails 를 파라미터로 받아올 수 있었음
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //데이터를 담아서, boardService 로 응답을 보냄
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    //전체 게시글 목록 조회
    @GetMapping("/posts")
    //BoardResponseDto 를 List 로 반환하는 타입, getListBoards 메소드 명, () 전부 Client 에게로 반환하므로 비워둠
    public List<BoardResponseDto> getListBoards() {
        //() 모든 데이터를 담아서, boardService 로 응답을 보냄
        return boardService.getListBoards();
    }

    //선택한 게시글 조회
    @GetMapping("/post/{id}")
    //BoardResponseDto 반환 타입, getBoards 메소드 명
    //@PathVariable: URL 경로에 변수를 넣기, Long id: 담을 데이터 --> 전체 게시글 목록에서 id 값으로 각각의 게시글을 구별
    public BoardResponseDto getBoards(@PathVariable Long id) {
        //id 값을 담아서, boardService 로 응답을 보냄
        return boardService.getBoard(id);
    }

    //선택한 게시글 수정(변경)
    @PutMapping("/post/{id}")
    //BoardResponseDto 반환 타입, updateBoard 메소드 명
    //@PathVariable: URL 경로에 변수를 넣기, Long id: 담을 데이터,
    //@RequestBody: HTTP Method 안의 body 값을 Mapping(key:value 로 짝지어줌), BoardRequestDto: 넘어오는 데이터를 받아주는 객체
    //@AuthenticationPrincipal: 인증 객체(Authentication)의 Principal 부분의 값을 가져온다
    //UserDetailsImpl userDetails: 인증 객체를 만들 때, Principal 부분에 userDetails 를 넣었기 때문에, userDetails 를 파라미터로 받아올 수 있었음
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //데이터를 담아서, boardService 로 응답을 보냄
        return boardService.updateBoard(id, requestDto, userDetails.getUser());
    }

    //선택한 게시글 삭제
    @DeleteMapping("/post/{id}")
    //MsgResponseDto 반환 타입, deleteBoard 메소드 명
    //@PathVariable: URL 경로에 변수를 넣기, Long id: 담을 데이터
    //@AuthenticationPrincipal: 인증 객체(Authentication)의 Principal 부분의 값을 가져온다
    //UserDetailsImpl userDetails: 인증 객체를 만들 때, Principal 부분에 userDetails 를 넣었기 때문에, userDetails 를 파라미터로 받아올 수 있었음
    public MsgResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id,userDetails.getUser());
        return new MsgResponseDto("삭제 성공", HttpStatus.OK.value());
    }
}