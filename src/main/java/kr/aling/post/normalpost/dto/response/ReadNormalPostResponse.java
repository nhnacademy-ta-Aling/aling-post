package kr.aling.post.normalpost.dto.response;

import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일반 게시물 조회 응답용 DTO
 *
 * @author : 이성준
 * @since : 1.0
 */
@NoArgsConstructor
@Getter
public class ReadNormalPostResponse {

    private Post post;
    private Long userNo;

    public ReadNormalPostResponse(NormalPost normalPost) {
        this.post = normalPost.getPost();
        this.userNo = normalPost.getUserNo();
    }
}
