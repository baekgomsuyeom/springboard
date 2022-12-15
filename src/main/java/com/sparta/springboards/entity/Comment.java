package com.sparta.springboards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.springboards.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();



    public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.board = board;
        this.user = user;
    }
    public Comment(CommentRequestDto commentRequestDto,Board board, User user, Comment comment){
        this.comment = commentRequestDto.getComment();
        this.username = user.getUsername();
        this.board = board;
        this.user = user;
        this.parent = comment;
    }


    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}