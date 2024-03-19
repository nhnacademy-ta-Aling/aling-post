package kr.aling.post.normalpost.dummy;

import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public class NormalPostDummy {

    public static NormalPost dummyNormalPost(Post post) {
        return NormalPost.builder()
                .post(post)
                .userNo(1L)
                .build();
    }
}
