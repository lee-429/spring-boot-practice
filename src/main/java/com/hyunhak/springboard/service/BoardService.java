package com.hyunhak.springboard.service;

import com.hyunhak.springboard.domain.Board;
import com.hyunhak.springboard.repository.BoardRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 비즈니스 로직 계층이라는 걸 Spring에 알려줌 (Service 역할)
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired // Spring이 자동으로 해당 타입의 객체(Bean)를 찾아서 주입해주는 기능
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 저장
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    // 전체 게시글 조회
    public ArrayList<Board> findAll() {
        return boardRepository.findAll();
    }

    // 게시글 단건 조회
    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    // 게시글 수정
    public Board update(Long id, Board board) {
        return boardRepository.update(id, board);
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.delete(id);
    }






}
