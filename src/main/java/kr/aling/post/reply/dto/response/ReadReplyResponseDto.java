package kr.aling.post.reply.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시물 조회시 그 게시물의 댓글을 조회하는 응답객체입니다.
 * 댓글번호, 댓글이 달린 게시물 번호, 내용, 작성시간, 수정시간, 작성자 정보
 * 대댓글인 경우 대상 댓글 번호가 포함되어있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class ReadReplyResponseDto {
    private final Long replyNo;
    private final Long postNo;
    private final Long parentReplyNo;
    private final String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime modifyAt;
}
