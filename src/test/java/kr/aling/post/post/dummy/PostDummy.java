package kr.aling.post.post.dummy;

import kr.aling.post.post.entity.Post;

/**
 * 테스트용 게시물 더미 객체
 *
 * @author : 이성준
 * @since 1.0
 */
public class PostDummy {

    public static Post postDummy() {
        return Post.builder()
                .content("content")
                .isOpen(true)
                .build();
    }

}
