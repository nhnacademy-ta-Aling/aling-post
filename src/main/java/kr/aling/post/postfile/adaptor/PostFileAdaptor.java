package kr.aling.post.postfile.adaptor;

import java.util.List;
import java.util.Map;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.postfile.dto.request.ReadPostFileRequestDto;

/**
 * 게시물에 포함된 파일 경로를 요청하는 데이터 액세스 인터페이스입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface PostFileAdaptor {
    /**
     * 여러 개의 게시물 목록에 포함된 파일을 요청하는 메서드입니다.
     *
     * @param requests 요청할 게시물에 대한 파일 목록
     * @return 게시물 식별 번호와 대응되는 파일 목록의 맵
     * @author : 이성준
     * @since : 1.0
     */
    Map<Long, List<GetFileInfoResponseDto>> readPostsFiles(List<ReadPostFileRequestDto> requests);

    /**
     * 여러 개의 파일에 대한 경로를 요청하는 메서드입니다. 1개의 게시물 조회시 사용합니다.
     *
     * @param postFilesNo 조회하려는 파일의 목록
     * @return 조회된 파일 경로 목록
     * @author : 이성준
     * @since : 1.0
     */
    List<GetFileInfoResponseDto> readPostFiles(List<Long> postFilesNo);
}
