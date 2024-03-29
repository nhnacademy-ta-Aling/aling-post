package kr.aling.post.postscrap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 스크랩 조회를 반환하는 Dto.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReadPostScrapsPostResponseDto {

    private Long postNo;
    private String content;
    private Boolean isDelete;
    private Boolean isOpen;
}
