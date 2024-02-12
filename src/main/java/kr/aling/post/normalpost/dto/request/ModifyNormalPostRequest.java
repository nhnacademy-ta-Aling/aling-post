package kr.aling.post.normalpost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일반 게시물 수정 요청 DTO
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class ModifyNormalPostRequest {

    @NotBlank
    private String content;

    @NotNull
    private Boolean isOpen;

    public ModifyPostRequest modifyPostRequest() {
        return new ModifyPostRequest(content,isOpen);
    }
}
