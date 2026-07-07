package com.hyunhak.springboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 수정(update)을 위한 데이터를 담는 DTO
public class BoardUpdateDto {

    // 2~30자까지 입력 가능
    @Size(min=2, max=30, message = "제목은 2~30자 사이로 입력해주세요.")
    // 공백 및 빈 문자열 입력 불가
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Size(min=2, max=1000, message = "내용은 2~1000자 사이로 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Size(min=2, max=10, message = "작성자는 2~10자 사이로 입력해주세요.")
    @NotBlank(message = "작성자를 입력해주세요.")
    private String writer;

}
