package com.hyunhak.springboard.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

    private Long id;        // 게시글 번호
    private String title;   // 제목
    private String content; // 내용
    private String writer;  // 작성자


    public Board() {

    }

    public Board(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}
