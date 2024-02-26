package kr.aling.post.normalpost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 일반 게시물 작성시 받는 요청 객체
 * 작성 내용에 포함되는 것은 게시물의 내용과 공개 여부를 입니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class CreateNormalPostRequestDto {

    @NotBlank
    @Size(max = 10_000)
    private String content;

    @NotNull
    private Boolean isOpen;

}
