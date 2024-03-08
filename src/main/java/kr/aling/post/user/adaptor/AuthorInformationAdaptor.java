package kr.aling.post.user.adaptor;

import java.util.Map;
import java.util.Set;
import kr.aling.post.post.dto.request.ReadAuthorInfoRequestDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.dto.request.ReadPostAuthorInfoRequestDto;

/**
 * 작성자 정보를 요청하는 데이터 액세스 인터페이스입니다.
 *
 *
 * @author : 이성준
 * @since : 1.0
 */
public interface AuthorInformationAdaptor {

    /**
     * 그룹 게시물 작성자의 정보를 요청하는 메서드입니다.
     *
     * @param bandPostUserNo 조회하려는 그룹회원 식별 번호
     * @return 조회된 그룹회원의 정보 응답객체
     * @author : 이성준
     * @since : 1.0
     */
    ReadUserInfoResponseDto readBandPostAuthorInfo(Long bandPostUserNo);

    /**
     * 일반 게시물 작성자의 정보를 요청하는 메서드입니다.
     *
     * @param normalPostUserNo 조회하려는 일반회원 식별 번호
     * @return 조회된 일반회원의 정보 응답객체
     * @author : 이성준
     * @since : 1.0
     */
    ReadUserInfoResponseDto readNormalPostAuthorInfo(Long normalPostUserNo);

    /**
     * 댓글 작성자 정보를 요청하는 메서드입니다
     *
     * @param requests 요청하려는 댓글 작성자 식별 번호 목록
     * @return 작성자 식별정보와 작성자 정보 응답 객체 맵
     * @author : 이성준
     * @since : 1.0
     */
    Map<Long, ReadUserInfoResponseDto> readReplyAuthorInfo(Set<ReadAuthorInfoRequestDto> requests);

    /**
     * 게시물 작성자 정보를 요청하는 메서드입니다
     *
     * @param requests 요청하려는 게시물 작성자 식별 번호 목록
     * @return 작성자 식별정보와 작성자 정보 응답 객체 맵
     * @author : 이성준
     * @since : 1.0
     */
    Map<Long, ReadUserInfoResponseDto> readPostAuthorInfo(Set<ReadPostAuthorInfoRequestDto> requests);
}
