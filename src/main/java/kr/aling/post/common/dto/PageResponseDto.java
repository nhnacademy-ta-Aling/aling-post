package kr.aling.post.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 페이징에 필요한 내용을 답는 페이징 응답 Dto.
 *
 * @param <T> 페이징 요소의 타입
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class PageResponseDto<T> {

    private Integer pageNumber;
    private Integer totalPages;
    private Long totalElements;
    private List<T> content;

}
