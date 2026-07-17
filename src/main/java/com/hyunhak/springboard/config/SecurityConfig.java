package com.hyunhak.springboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Spring 설정 클래스라는 의미
// Spring Boot가 실행될 때 이 클래스를 설정 파일로 등록함
@Configuration
public class SecurityConfig {

    // @Bean: 객체를 Spring이 관리하도록 등록
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // React 요청 허용
            .cors(cors -> {})

            // React + REST API 사용으로 CSRF 비활성화
            .csrf(csrf -> csrf.disable())

            // SecurityContext를 세션에 자동 저장
            .securityContext(context ->
                context.requireExplicitSave(false)
            )

            // URL별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth

                // 회원가입, 로그인은 누구나 접근 가능
                .requestMatchers(
                    "/api/members",
                    "/api/members/login",
                    "/api/members/logout")
                .permitAll()

                // 게시글 조회(GET)는 누구나 가능
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/boards/**"
                )
                .permitAll()

                // 나머지 요청은 로그인 필요
                .anyRequest()
                .authenticated()
            );

        // 설정한 SecurityFilterChain 반환
        return http.build();
    }

    @Bean
    // 비밀번호 암호화 기능을 제공하는 객체 생성
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // AuthenticationManager 객체를 Spring Bean으로 등록
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
