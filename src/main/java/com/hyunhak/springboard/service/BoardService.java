package com.hyunhak.springboard.service;

import com.hyunhak.springboard.domain.Board;
import com.hyunhak.springboard.dto.BoardCreateDto;
import com.hyunhak.springboard.dto.BoardResponseDto;
import com.hyunhak.springboard.dto.BoardUpdateDto;
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
    public Board save(BoardCreateDto dto) {

        // DTO를 Board 객체로 변환
        Board board = new Board();

        // DTO의 데이터를 Board에 복사
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setWriter(dto.getWriter());

        // Repository에 저장
        return boardRepository.save(board);
    }

    // 전체 게시글 조회
    public ArrayList<BoardResponseDto> findAll() {

        // Repository에서 게시글 목록 조회
        ArrayList<Board> boards = boardRepository.findAll();

        // 화면에 전달할 DTO 목록
        ArrayList<BoardResponseDto> responseDtos = new ArrayList<>();

        // Board -> BoardResponseDto 변환
        for (Board board : boards) {
            BoardResponseDto dto = new BoardResponseDto();

            dto.setId(board.getId());
            dto.setTitle(board.getTitle());
            dto.setContent(board.getContent());
            dto.setWriter(board.getWriter());

            responseDtos.add(dto);
        }

        return responseDtos;
    }

    // 게시글 단건 조회
    public BoardResponseDto findById(Long id) {

        // Repository에서 게시글 조회
        Board board = boardRepository.findById(id);

        // Board -> BoardResponseDto 변환
        BoardResponseDto dto = new BoardResponseDto();

        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setWriter(board.getWriter());

        return dto;
    }

    // 게시글 수정
    public Board update(Long id, BoardUpdateDto dto) {

        // DTO -> Board 변환
        Board board = new Board();

        board.setTitle(dto.getTitle());
        board.setWriter(dto.getWriter());
        board.setContent(dto.getContent());

        // Repository에 수정 요청
        return boardRepository.update(id, board);
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.delete(id);
    }






}
