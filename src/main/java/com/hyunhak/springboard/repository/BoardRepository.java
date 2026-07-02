package com.hyunhak.springboard.repository;

import com.hyunhak.springboard.domain.Board;
import java.util.ArrayList;

public interface BoardRepository {

    // 게시글 저장
    Board save(Board board);

    // 게시글 전체 조회
    ArrayList<Board> findAll();

    // 게시글 ID 별 조회
    Board findById(Long id);

    // 게시글 수정
    Board update(Long id, Board board);

    // 게시글 삭제
    void delete(Long id);
}
