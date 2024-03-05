package kr.aling.post.reply.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 조회시 그 게시물의 댓글을 조회하는 응답객체입니다.
 * 댓글번호, 댓글이 달린 게시물 번호, 작성자 번호, 작성자 이름
 * 대댓글인 경우 대상 댓글, 내용, 작성시간, 수정시간이 포함되어있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ReadReplyResponseDto {
    private final Long replyNo;
    private final Long postNo;
    private final Long userNo;
    private final String userName;
    private final Long parentReplyNo;
    private final String content;
    private final LocalDateTime createAt;
    private final LocalDateTime modifyAt;
}
