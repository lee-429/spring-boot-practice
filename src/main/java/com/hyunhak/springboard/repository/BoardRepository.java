package com.hyunhak.springboard.repository;

import com.hyunhak.springboard.domain.Board;
import java.util.ArrayList;

public interface BoardRepository {

    Board save(Board board);

    ArrayList<Board> findAll();

    Board findById(Long id);

    Board update(Board board);

    void delete(Long id);
}
