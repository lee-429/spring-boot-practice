package com.hyunhak.springboard.dto.member;

import lombok.Getter;

// 로그인 성공 후 JWT Access Token을 응답하기 위한 DTO
@Getter
public class TokenResponseDto {

    private final String accessToken;

    public TokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
