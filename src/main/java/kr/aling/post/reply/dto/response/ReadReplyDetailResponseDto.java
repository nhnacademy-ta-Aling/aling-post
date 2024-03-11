package kr.aling.post.reply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 정보 조회 응답 객체입니다.
 * 댓글의 전반적인 정보와 작성자 정보가 있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ReadReplyDetailResponseDto {

    private ReadReplyResponseDto reply;
    private ReadUserInfoResponseDto writer;
}
