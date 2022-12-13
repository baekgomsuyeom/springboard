package com.sparta.springboards.controller;

import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.security.UserDetailsImpl;
import com.sparta.springboards.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("/comment/{commentId}")
    public MsgResponseDto commentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        return commentLikeService.CommentLike(userDetails.getUser(),commentId);
    }

}
