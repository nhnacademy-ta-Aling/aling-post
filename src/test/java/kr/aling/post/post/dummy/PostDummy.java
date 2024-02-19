package kr.aling.post.post.dummy;

import kr.aling.post.post.entity.Post;

public class PostDummy {

    public static Post postDummy() {
        return Post.builder()
                .content("content")
                .isOpen(false)
                .build();
    }
}
