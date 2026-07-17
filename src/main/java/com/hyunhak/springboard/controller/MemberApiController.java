package com.hyunhak.springboard.controller;

import com.hyunhak.springboard.dto.LoginDto;
import com.hyunhak.springboard.dto.MemberCreateDto;
import com.hyunhak.springboard.dto.MemberResponseDto;
import com.hyunhak.springboard.entity.MemberEntity;
import com.hyunhak.springboard.exception.LoginFailedException;
import com.hyunhak.springboard.exception.LoginRequiredException;
import com.hyunhak.springboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping
    public MemberResponseDto join(@RequestBody @Valid MemberCreateDto dto) {

        MemberEntity member = memberService.join(dto);

        return new MemberResponseDto(member);
    }

    // 로그인
    @PostMapping("/login")
    public MemberResponseDto login(@RequestBody @Valid LoginDto dto, HttpSession session) {

        // 로그인 검증 후 회원 정보 반환
        MemberEntity member = memberService.login(dto);

        // 로그인한 회원 정보를 세션에 저장
        session.setAttribute("loginMember", member);

        return new MemberResponseDto(member);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok().build();
    }

}
