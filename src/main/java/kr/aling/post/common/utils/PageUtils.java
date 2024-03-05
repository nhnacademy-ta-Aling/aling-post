package kr.aling.post.common.utils;

import java.util.List;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * PageResponseDto 관련 유틸 클래스
 *
 * @author : 이성준
 * @since : 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageUtils {

    /**
     * org.springframework.data.domain.Page 를 kr.aling.post.common.dto.PageResponseDto 로 변환
     *
     * @param page org.springframework.data.domain.Page 객체
     * @param <T>  페이징 요소의 타입
     * @return 변환이 완료된 kr.aling.post.common.dto.PageResponseDto 객체
     * @author : 이성준
     * @see org.springframework.data.domain.Page
     * @since 1.0
     */
    public static <T> PageResponseDto<T> convert(Page<T> page) {
        return new PageResponseDto<>(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent()
        );
    }


    public static PageResponseDto<ReadReplyResponseDto> convert(Page<Reply> page,
                                                                List<ReadReplyResponseDto> pageContent) {
        return new PageResponseDto<>(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                pageContent
        );
    }
}
