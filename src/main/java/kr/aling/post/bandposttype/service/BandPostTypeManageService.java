package kr.aling.post.bandposttype.service;

import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.request.ModifyBandPostTypeRequestDto;

/**
 * 그룹 게시글 분류 관리 service.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface BandPostTypeManageService {

    /**
     * 그룹 게시글 분류를 생성 하는 메서드 입니다.
     *
     * @param requestDto 그룹 게시글 분류 생성에 필요한 정보를 담은 dto
     */
    void makeBandPostType(CreateBandPostTypeRequestDto requestDto);

    /**
     * 그룹 게시글 분류 수정을 위한 메서드입니다.
     *
     * @param postTypeNo 수정할 그룹 게시글 분류 번호
     * @param requestDto 수정할 그룹 게시글 분류 정보 dto
     */
    void updateBandPostType(Long postTypeNo, ModifyBandPostTypeRequestDto requestDto);

    /**
     * 그룹 게시글 분류 삭제를 위한 메서드입니다.<br>
     *
     * @param postTypeNo 그룹 게시글 분류 번호
     */
    void deleteBandPostType(Long postTypeNo);
}
