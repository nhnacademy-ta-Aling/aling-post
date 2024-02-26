package kr.aling.post.post.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 조회시 응답 객체입니다.
 * 게시물 번호, 내용, 작성시간, 수정시간을 반환합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class ReadPostResponseDto {

    private Long postNo;
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

}
