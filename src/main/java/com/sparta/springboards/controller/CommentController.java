package com.sparta.springboards.controller;

import com.sparta.springboards.dto.CommentRequestDto;
import com.sparta.springboards.dto.CommentResponseDto;
import com.sparta.springboards.dto.MsgResponseDto;
import com.sparta.springboards.security.UserDetailsImpl;
import com.sparta.springboards.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/{boardId}/{commentId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long boardId,@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.createComment(boardId,commentId, commentRequestDto, userDetails.getUser()));
    }

    //댓글 수정(변경)
    @PutMapping("/{boardId}/{cmtId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long boardId, @PathVariable Long cmtId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(boardId, cmtId, commentRequestDto, userDetails.getUser()));
    }

    //댓글 삭제
    @DeleteMapping("/{boardId}/{cmtId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long boardId, @PathVariable Long cmtId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(boardId, cmtId, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("삭제 성공", HttpStatus.OK.value()));
    }

    @PostMapping("/like/{commentId}")
    public MsgResponseDto commentLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        return commentService.CommentLike(userDetails.getUser(),commentId);
    }
}
