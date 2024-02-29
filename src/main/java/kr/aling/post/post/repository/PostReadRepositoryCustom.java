package kr.aling.post.post.repository;

import java.util.List;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * QueryDsl 게시물 조회를 위한 interface.
 *
 * @author 이수정
 * @since 1.0
 */
@NoRepositoryBean
public interface PostReadRepositoryCustom {

    /**
     * 게시물 스크랩 조회용 게시물 정보를 조회합니다.
     *
     * @param postNos 조회할 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용 리스트
     */
    List<ReadPostScrapsPostResponseDto> getPostInfoForScrap(List<Long> postNos);
}
