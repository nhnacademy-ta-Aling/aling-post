package kr.aling.post.postfile.repository;

import java.util.List;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@NoRepositoryBean
public interface PostFileReadRepositoryCustom {

    List<PostFileQueryDto> getPostFileByPostNo(Long postNo);
}
