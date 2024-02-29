package kr.aling.post.postfile.repository;

import java.util.List;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * PostFile QueryDsl Custom Repository.
 *
 * @author 박경서
 * @since 1.0
 **/
@NoRepositoryBean
public interface PostFileReadRepositoryCustom {

    /**
     * 게시글 첨부 파일 번호 리스트 조회.
     *
     * @param postNo 게시글 번호
     * @return 첨부 파일 번호 리스트
     */
    List<PostFileQueryDto> getPostFileByPostNo(Long postNo);
}
