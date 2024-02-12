package kr.aling.post.post.dto.response;

import java.time.LocalDateTime;
import kr.aling.post.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : 이성준
 */
@NoArgsConstructor
@Getter
public class ReadPostResponse {

    private Long postNo;
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    public ReadPostResponse(Post post) {
        this.postNo = post.getPostNo();
        this.content = post.getContent();
        this.createAt = post.getCreateAt();
        this.modifyAt = post.getModifyAt();
    }

}
