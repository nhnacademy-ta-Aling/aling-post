package kr.aling.post.normalpost.dto.request;

import kr.aling.post.post.dto.request.CreatePostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateNormalPostRequest {
    private String content;
    private Boolean isOpen;

    public CreatePostRequest getCreatePostRequest() {
        return new CreatePostRequest(content, isOpen);
    }

    public Boolean isOpen() {
        return isOpen;
    }
}
