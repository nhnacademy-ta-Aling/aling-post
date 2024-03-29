package kr.aling.post.reply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 작성자의 이름을 받아오기 위한 응답객체입니다. 댓글 작성자의 번호와 이름, 프로필 사진의 경로가 담겨있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserInfoResponseDto {

    private Long userNo;
    private String username;
    private String profilePath;
}
