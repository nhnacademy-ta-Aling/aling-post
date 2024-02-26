package kr.aling.post.reply.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 조회시 그 게시물의 댓글을 조회하는 응답객체입니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReadReplyResponseDto {
    private final Long postNo;
    private final Long replyNo;
    private final Long parentReplyNo;
    private final String content;
    private final Long userNo;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;
}
