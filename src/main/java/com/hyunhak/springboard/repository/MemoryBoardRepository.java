package com.hyunhak.springboard.repository;

import com.hyunhak.springboard.domain.Board;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

@Repository // 데이터(DB/메모리) 접근 계층이라는 걸 Spring에 알려줌 (Repository 역할)
public class MemoryBoardRepository implements BoardRepository {

    // 1. board를 저장할 공간을 하나 만든다.
    ArrayList<Board> boardList = new ArrayList<>();

    // 2. id 변수를 만든다.
    private Long id = 0L;

    // 게시글 저장
    @Override
    public Board save(Board board) {

        board.setId(++id);      // board에 id를 넣어준다. (자동증가)
        boardList.add(board);   // boardList에 board를 넣어준다

        return board;
    }

    // 게시글 전체 조회
    @Override
    public ArrayList<Board> findAll() {
        return boardList;
    }

    // 게시글 ID 별 조회
    @Override
    public Board findById(Long id) {

        for (int i = 0; i < boardList.size(); i++) {

            // boardList 안에 있는 board 객체와 비교
            // Long 타입은 객체이기에 equals() 사용 가능
            if (id.equals(boardList.get(i).getId())) {
                return boardList.get(i);
            }

        }

        return null;
    }

    // 게시글 수정
    @Override
    public Board update(Long id, Board board) {

        for (int i = 0; i < boardList.size(); i++) {
            if (id.equals(boardList.get(i).getId())) {
                boardList.get(i).setTitle(board.getTitle());
                boardList.get(i).setContent(board.getContent());
                boardList.get(i).setWriter(board.getWriter());

                return boardList.get(i);
            }
        }

        return null;
    }

    // 게시글 삭제
    @Override
    public void delete(Long id) {

        for (int i = 0; i < boardList.size(); i++) {
            if (id.equals(boardList.get(i).getId())) {
                boardList.remove(i);
                break; // 삭제 후에도 for문 계속 돌면 인덱스가 꼬일 수 있기에 break
            }
        }

    }
}
