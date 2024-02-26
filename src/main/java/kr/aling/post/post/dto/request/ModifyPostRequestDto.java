package kr.aling.post.post.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 작성시 응답 객체입니다.
 * 테이블 설계상 단독으로 사용되지는 않고 일반 게시물, 그룹 게시물의 요청 객체에서 파생하여 사용하고 있습니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class ModifyPostRequestDto {

    @NotBlank
    private String content;

    @NotNull
    private Boolean isOpen;

}
