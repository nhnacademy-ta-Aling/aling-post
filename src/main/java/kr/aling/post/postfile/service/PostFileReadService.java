package kr.aling.post.postfile.service;

import java.util.List;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public interface PostFileReadService {

    List<PostFileQueryDto> getPostFileNoList(Long postNo);
}
