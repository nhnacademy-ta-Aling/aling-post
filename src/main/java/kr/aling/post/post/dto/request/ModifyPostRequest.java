package kr.aling.post.post.dto.request;

import lombok.Getter;

@Getter
public class ModifyPostRequest {

    private String content;
    private boolean isOpen;

    public ModifyPostRequest(String content, boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
    }
}
