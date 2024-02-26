package kr.aling.post.bandposttype.service;

import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeDefaultRequestDto;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;

/**
 * 그룹 게시글 분류 관리 service.
 *
 * @author 정유진
 * @since 1.0
 **/
public interface BandPostTypeManageService {

    /**
     * 기본 게시글 분류 타입을 생성 하는 메서드 입니다.
     *
     * @param requestDto 기본 게시글 분류 타입 생성에 필요한 정보를 담은 dto
     */
    void makeDefaultType(CreateBandPostTypeDefaultRequestDto requestDto);

    /**
     * 게시글 분류 타입을 생성 하는 메서드 입니다.
     *
     * @param requestDto 기본 게시글 분류 타입 생성에 필요한 정보를 담은 dto
     */
    void makeBandPostType(CreateBandPostTypeRequestDto requestDto);
}
