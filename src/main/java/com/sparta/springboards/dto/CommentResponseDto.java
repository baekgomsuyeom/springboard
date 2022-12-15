package com.sparta.springboards.dto;

import com.sparta.springboards.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor


//댓글 표시
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private int CommentLike;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto
            > childComment = new ArrayList<>();

    public CommentResponseDto(Comment comment, int cnt) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.CommentLike = cnt;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();

    }

    public CommentResponseDto(Comment comment, int cnt, List<CommentResponseDto> childcomment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.CommentLike = cnt;
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.childComment = childcomment;
    }


}