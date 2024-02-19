package kr.aling.post.bandpost.dummy;

import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.post.entity.Post;

public class BandPostDummy {

    public static BandPost bandPostDummy(Post post, BandPostType bandPostType) {
        return BandPost.builder()
                .postNo(post.getPostNo())
                .post(post)
                .bandPostType(bandPostType)
                .bandUserNo(1L)
                .title("title")
                .build();
    }
}
