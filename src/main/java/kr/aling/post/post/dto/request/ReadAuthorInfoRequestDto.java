package kr.aling.post.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 댓글 작성자의 이름을 조회하기 위한 요청 객체입니다. 이 객체를 이용해 유저 서버에 작성자들의 번호를 주고 작성자 번호와 이름이 매핑된 객체를 받습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ReadAuthorInfoRequestDto {

    private Long userNo;
}

