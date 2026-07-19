package com.hyunhak.springboard.controller.api;

import com.hyunhak.springboard.dto.member.LoginDto;
import com.hyunhak.springboard.dto.member.MemberCreateDto;
import com.hyunhak.springboard.dto.member.MemberResponseDto;
import com.hyunhak.springboard.dto.member.TokenResponseDto;
import com.hyunhak.springboard.entity.MemberEntity;
import com.hyunhak.springboard.security.MemberPrincipal;
import com.hyunhak.springboard.security.jwt.TokenProvider;
import com.hyunhak.springboard.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public MemberApiController(
        MemberService memberService,
        AuthenticationManager authenticationManager,
        TokenProvider tokenProvider) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    // 회원가입
    @PostMapping
    public MemberResponseDto join(@RequestBody @Valid MemberCreateDto dto) {

        MemberEntity member = memberService.join(dto);

        return new MemberResponseDto(member);
    }

    // 로그인
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginDto dto) {

        // 입력받은 로그인 정보로 Spring Security 인증 요청 생성
        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.getLoginId(),
                    dto.getPassword()
                )
            );

        // 인증 성공 후 로그인 ID 추출
        String loginId = authentication.getName();

        // 로그인 ID를 기반으로 JWT Access Token 생성
        String accessToken = tokenProvider.createAccessToken(loginId);

        // 생성된 Access Token 반환
        return new TokenResponseDto(accessToken);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    // 로그인한 회원 정보 반환
    @GetMapping("/me")
    public MemberResponseDto getMyInfo(
        // Spring Security에서 인증된 로그인 사용자 정보
        @AuthenticationPrincipal MemberPrincipal memberPrincipal) {

        // MemberPrincipal에서 회원 엔티티 추출
        MemberEntity member = memberPrincipal.getMember();

        // 회원 정보를 DTO로 변환하여 반환
        return new MemberResponseDto(member);

    }
}
