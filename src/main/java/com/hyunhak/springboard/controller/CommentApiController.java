package com.hyunhak.springboard.controller;

import com.hyunhak.springboard.dto.CommentCreateDto;
import com.hyunhak.springboard.dto.CommentResponseDto;
import com.hyunhak.springboard.dto.CommentUpdateDto;
import com.hyunhak.springboard.entity.CommentEntity;
import com.hyunhak.springboard.entity.MemberEntity;
import com.hyunhak.springboard.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/{boardId}/comments")
public class CommentApiController {

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회
    @GetMapping
    public List<CommentResponseDto> getComments(@PathVariable Long boardId) {
        return commentService.findByBoardId(boardId);
    }

    // 댓글 작성
    @PostMapping
    public CommentResponseDto save(
        @PathVariable Long boardId,
        @RequestBody @Valid CommentCreateDto dto,
        HttpSession session) {

        // 세션에서 현재 로그인한 사용자 정보 가져오기
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        // 댓글 저장 후 저장된 댓글 Entity 반환
        CommentEntity comment = commentService.save(dto, loginMember, boardId);

        // Entity를 응답용 DTO로 변환하여 반환
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public CommentResponseDto update(
        @PathVariable Long commentId,
        @RequestBody @Valid CommentUpdateDto dto,
        HttpSession session
    ) {

        // 세션에서 현재 로그인한 사용자 정보 가져오기
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        // 댓글 수정 후 수정된 댓글 Entity 반환
        CommentEntity comment = commentService.update(commentId, dto, loginMember);

        // Entity를 응답용 DTO로 변환하여 반환
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId, HttpSession session) {

        // 세션에서 현재 로그인한 사용자 정보 가져오기
        MemberEntity loginMember = (MemberEntity) session.getAttribute("loginMember");

        // 댓글 삭제
        commentService.delete(commentId, loginMember);

        // 삭제 성공 응답 반환
        return ResponseEntity.ok().build();
    }




}
