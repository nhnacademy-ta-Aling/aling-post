package kr.aling.post.bandposttype.service;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;

/**
 * 그룹 게시글 분류 조회 service.
 *
 * @author : 정유진
 * @since : 1.0
 **/
public interface BandPostTypeReadService {

    /**
     * 특정 그룹의 그룹 게시글 분류 리스트를 조회하기 위한 메서드 입니다.
     *
     * @param bandNo 그룹 번호
     * @return 그룹 게시글 분류 dto 리스트
     */
    List<GetBandPostTypeResponseDto> getBandPostTypeList(Long bandNo);
}
