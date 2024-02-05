package kr.aling.post.post.dto.request;

import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String content;
    private Boolean isOpen;

    public CreatePostRequest(String content, boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
    }

    public Boolean isOpen() {
        return isOpen;
    }
}
