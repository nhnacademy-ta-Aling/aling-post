package kr.aling.post.postfile.dto.response;

import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시물의 파일 정보 응답 객체입니다.
 * 게시물 식별 정보와 해당 게시물의 파일 정보 목록이 담겨있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class ReadPostFileResponseDto {

    private Long postNo;

    private List<GetFileInfoResponseDto> files;
}
