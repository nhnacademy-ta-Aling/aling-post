package kr.aling.post.post.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시물 작성 요청시 사용되는 DTO 입니다.
 * 테이블 설계상 단독으로 사용되지는 않고 일반 게시물, 그룹 게시물 Request DTO 에서 파생되어 사용될 것으로 예상합니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@NoArgsConstructor
@Getter
public class CreatePostRequest {

    @NotBlank
    private String content;

    @NotNull
    private Boolean isOpen;

    public CreatePostRequest(String content, boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
    }

}
