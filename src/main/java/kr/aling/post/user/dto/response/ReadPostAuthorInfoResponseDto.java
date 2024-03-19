package kr.aling.post.user.dto.response;

import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import lombok.Getter;

/**
 * 게시물의 작성자 정보 응답 객체입니다. 게시물 식별 번호와 해당 게시물의 작성자 정보가 담겨있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
public class ReadPostAuthorInfoResponseDto {

    private Long postNo;
    private ReadUserInfoResponseDto userInfo;
}
