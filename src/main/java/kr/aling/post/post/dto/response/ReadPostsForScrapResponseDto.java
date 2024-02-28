package kr.aling.post.post.dto.response;

import java.util.List;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 스크랩 조회용 게시물을 조회하고 받은 응답 Dto.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReadPostsForScrapResponseDto {

    private List<ReadPostScrapsPostResponseDto> infos;
}
