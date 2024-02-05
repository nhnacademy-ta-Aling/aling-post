package kr.aling.post.normalpost.dto.request;

import kr.aling.post.post.dto.request.ModifyPostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyNormalPostRequest {
    private String content;
    private Boolean isOpen;

    public ModifyPostRequest getModifyPostRequest() {
        return new ModifyPostRequest(content,isOpen);
    }
}
