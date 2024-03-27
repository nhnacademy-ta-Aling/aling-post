package kr.aling.post.normalpost.dummy;

import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class NormalPostDummy {

    public static NormalPost normalPostDummy() {
        Post post = PostDummy.postDummy();
        Long userNo = 1L;

        return NormalPost.builder()
                .post(post)
                .userNo(userNo)
                .build();
    }
}
