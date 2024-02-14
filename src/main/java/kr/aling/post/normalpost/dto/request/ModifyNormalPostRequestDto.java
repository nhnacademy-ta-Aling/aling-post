package kr.aling.post.normalpost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일반 게시물 수정시 받는 요청 객체
 * 수정 내용에 포함되는 것은 게시물의 내용과 공개 여부를 입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class ModifyNormalPostRequestDto {

    @NotBlank
    private String content;

    @NotNull
    private Boolean isOpen;

}