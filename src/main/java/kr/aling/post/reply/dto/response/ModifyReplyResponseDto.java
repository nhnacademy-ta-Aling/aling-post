package kr.aling.post.reply.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 수정시 응답 객체입니다.
 * 수정한 댓글 번호와 수정된 내용, 마지막 수정 시간을 담고 있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */

@Getter
@AllArgsConstructor
public class ModifyReplyResponseDto {
    private Long replyNo;
    private String content;
    private final LocalDateTime modifyAt;
}
