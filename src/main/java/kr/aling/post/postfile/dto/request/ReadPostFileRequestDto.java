package kr.aling.post.postfile.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물의 파일 정보 요청 객체입니다. 게시물 식별 정보와 게시물의 파일 식별 번호 목록이 담겨있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ReadPostFileRequestDto {

    private Long postNo;

    private List<Long> fileNoList;
}
