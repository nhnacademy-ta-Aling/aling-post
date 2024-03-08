package kr.aling.post.user.dto.request;

import kr.aling.post.post.dto.request.ReadAuthorInfoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 작성자 정보 요청 객체입니다.
 * 대상 게시물 번호, 그룹 게시물인지의 여부, 요청할 작성자 식별 정보입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ReadPostAuthorInfoRequestDto {

    private Long postNo;

    private boolean isBandPost;
    private ReadAuthorInfoRequestDto authorInfo;
}
