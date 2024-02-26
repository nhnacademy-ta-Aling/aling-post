package kr.aling.post.reply.service;

import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import org.springframework.data.domain.Pageable;

/**
 * 댓글 조회용 서비스 레이어.
 *
 * @author : 이성준
 * @since 1.0
 */

public interface ReplyReadService {

    /**
     * 게시물 번호를 조건으로 댓글을 조회합니다. 페이징을 이용해 한번에 불러올 갯수를 조절합니다.
     *
     * @param postNo 찾을 댓글들의 게시물 번호
     * @param pageable 페이지네이션 정보
     * @return 조회된 댓글 페이지 응답 객체
     * @author : 이성준
     * @since 1.0
     */
    PageResponseDto<ReadReplyResponseDto> readRepliesByPostNo(Long postNo, Pageable pageable);


}
