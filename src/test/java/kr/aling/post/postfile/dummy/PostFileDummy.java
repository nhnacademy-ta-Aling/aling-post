package kr.aling.post.postfile.dummy;

import kr.aling.post.post.entity.Post;
import kr.aling.post.postfile.entity.PostFile;

public class PostFileDummy {

    public static PostFile postFileDummy(Post post) {
        return PostFile.builder()
                .post(post)
                .fileNo(1L)
                .build();
    }
}
