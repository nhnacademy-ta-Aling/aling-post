package kr.aling.post.bandposttype.repository;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;

/**
 * 그룹 게시글 분류 repository custom.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface BandPostTypeReadRepositoryCustom {

    /**
     * 그룹 게시글 분류 존재(중복) 여부를 확인 하는 메서드입니다.<br>
     *
     * @param name   그룹 게시글 분류 이름
     * @param bandNo 그룹 번호
     * @return 존재(중복) 여부
     */
    boolean existsByNameAndBandNo(String name, Long bandNo);

    /**
     * 특정 그룹의 그룹 게시글 분류 리스트를 조회하는 메서드입니다.
     *
     * @param bandNo 그룹 번호
     * @return 그룹 게시글 분류 dto 리스트
     */
    List<GetBandPostTypeResponseDto> getBandPostTypeListByBandNo(Long bandNo);
}
