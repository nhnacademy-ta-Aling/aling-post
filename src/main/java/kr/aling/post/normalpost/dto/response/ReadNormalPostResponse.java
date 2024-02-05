package kr.aling.post.normalpost.dto.response;

import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReadNormalPostResponse {

    Post post;
    Long userNo;

    public ReadNormalPostResponse(NormalPost normalPost) {
        this.post = normalPost.getPost();
        this.userNo = normalPost.getUserNo();
    }
}
