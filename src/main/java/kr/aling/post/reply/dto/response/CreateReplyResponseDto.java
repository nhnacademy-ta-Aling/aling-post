package kr.aling.post.reply.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 작성 요청시 응답 객체.
 *
 * @author : 이성준
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class CreateReplyResponseDto {
    private final Long replyNo;
    private final Long parentReplyNo;
    private final Long userNo;
    private final Long postNo;
    private final String content;
    private final LocalDateTime createAt;
}
