package com.hyunhak.springboard.controller;

import com.hyunhak.springboard.dto.BoardCreateDto;
import com.hyunhak.springboard.dto.BoardResponseDto;
import com.hyunhak.springboard.dto.BoardUpdateDto;
import com.hyunhak.springboard.entity.BoardEntity;
import com.hyunhak.springboard.entity.MemberEntity;
import com.hyunhak.springboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// REST API 컨트롤러임을 나타냄
// 반환값을 View(HTML)로 해석하지 않고 JSON 형태의 응답으로 반환
@RestController

// 이 컨트롤러에서 처리할 요청의 공통 URL을 지정
// 예) GET /api/boards, POST /api/boards, GET /api/boards/{id}
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @Autowired
    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    /*
     -> Page 객체를 JSON으로 반환하고 페이지네이션은 React에서 처리
     */
    // 게시글 목록
    @GetMapping
    public Page<BoardResponseDto> list(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String keyword,
        @PageableDefault(size = 10, sort = "id", direction = Direction.DESC) Pageable pageable
    ) {

        // 검색어가 없으면 전체 게시글 조회
        if (keyword == null || keyword.isBlank()) {
            return boardService.findAll(pageable);
        }

        // 검색 종류가 제목이면 제목 검색
        if ("title".equals(type)) {
            return boardService.searchByTitle(keyword, pageable);
        }

        // 검색 종류가 작성자면 작성자 검색
        if ("writer".equals(type)) {
            return boardService.searchByWriter(keyword, pageable);
        }

        // 그 외에는 제목 + 내용 검색
        return boardService.search(keyword, pageable);
    }

    // 게시글 저장
    @PostMapping
    public BoardEntity save(@ModelAttribute @Valid BoardCreateDto dto, HttpSession session) {

        // 로그인 회원 조회
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        return boardService.save(dto, loginMember);
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public BoardResponseDto findById(@PathVariable Long id) {

        // 게시글 조회
        return boardService.findById(id);
    }

    // 게시글 수정 (REST API에서는 수정 요청에 PUT 메서드를 사용)
    @PutMapping(
        value = "/{id}",
        consumes = "multipart/form-data"
    )
    public BoardEntity update(
        @PathVariable Long id,
        @ModelAttribute @Valid BoardUpdateDto dto,
        HttpSession session) {

        // 로그인 회원 조회
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        return boardService.update(id, dto, loginMember);
    }

    // 게시글 삭제 (REST API에서는 삭제 요청에 DELETE 메서드를 사용)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, HttpSession session) {

        // 로그인 회원 조회
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        boardService.delete(id, loginMember);
    }
}
