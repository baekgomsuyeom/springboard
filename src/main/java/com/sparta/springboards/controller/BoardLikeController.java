package com.sparta.springboards.controller;

import com.sparta.springboards.dto.BoardResponseDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.security.UserDetailsImpl;
import com.sparta.springboards.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board/like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/{boardId}")
    public ResponseEntity<MsgResponseDto> saveBoardLike(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardLikeService.saveBoradLike(boardId, userDetails.getUser()));
    }
}
