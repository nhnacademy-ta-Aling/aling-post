package kr.aling.post.normalpost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.aling.post.post.dto.request.CreatePostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 일반 게시물 작성 요청 DTO
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class CreateNormalPostRequest {

    @NotBlank
    private String content;

    @NotNull
    private Boolean isOpen;

    /**
     * Gets create post request.
     *
     * @return the create post request
     */
    public CreatePostRequest getCreatePostRequest() {
        return new CreatePostRequest(content, isOpen);
    }

}
