package com.hyunhak.springboard.controller.api;

import com.hyunhak.springboard.dto.member.LoginDto;
import com.hyunhak.springboard.dto.member.MemberCreateDto;
import com.hyunhak.springboard.dto.member.MemberResponseDto;
import com.hyunhak.springboard.entity.MemberEntity;
import com.hyunhak.springboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public MemberApiController(MemberService memberService, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
    }

    // 회원가입
    @PostMapping
    public MemberResponseDto join(@RequestBody @Valid MemberCreateDto dto) {

        MemberEntity member = memberService.join(dto);

        return new MemberResponseDto(member);
    }

    // 로그인
    @PostMapping("/login")
    public MemberResponseDto login(@RequestBody @Valid LoginDto dto) {

        // Spring Security 인증 후 회원 정보 반환
        MemberEntity member = memberService.login(dto);

        return new MemberResponseDto(member);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {

        // SecurityContext에 저장된 인증 정보 제거
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().build();
    }

}
