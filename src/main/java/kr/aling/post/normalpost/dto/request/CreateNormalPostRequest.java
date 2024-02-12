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
     * 일반 게시물 작성 요청을 기반하여 게시물 작성 요청 DTO 를 추출하는 메서드.
     * 현재 같은 필드를 가지고 있지만 차후 엔티티 변경 혹은 DTO 의 필요한 값의 변경이 대비
     *
     * @return Post 엔티티에 대한 생성 요청 DTO
     */
    public CreatePostRequest createPostRequest() {
        return new CreatePostRequest(content, isOpen);
    }

}
