package com.sparta.springboards.entity;

import com.sparta.springboards.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.board = board;
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}