package kr.aling.post.common.feign.client;

import java.util.List;
import java.util.Set;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.dto.request.ReadPostAuthorInfoRequestDto;
import kr.aling.post.user.dto.response.IsExistsUserResponseDto;
import kr.aling.post.user.dto.response.ReadPostAuthorInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * aling-user 서버에 대한 오픈 페인 클라이언트 인터페이스 입니다.
 *
 * @author 박경서
 * @since 1.0
 **/
@FeignClient("aling-user")
public interface UserFeignClient {

    @GetMapping("/api/v1/band-users/{bandUserNo}")
    ReadUserInfoResponseDto requestBandPostUserInfo(@PathVariable("bandUserNo") Long bandUserNo);
    @GetMapping("/api/v1/users/{userNo}")
    ReadUserInfoResponseDto requestUserInfo(@PathVariable("userNo") Long userNo);

    @GetMapping("/api/v1/users")
    List<ReadUserInfoResponseDto> requestUserInfos(@RequestParam List<Long> userNoList);

    /**
     * 게시물 작성자 정보 요청입니다.
     * 여러개의 게시물을 한번에 요청하기 때문에 요청에 대한 정보를 Request Body 에 담습니다.
     *
     * @param requests 작성자 정보를 요청할 요청 객체 목록
     * @return 게시물의 작성자 정보 응답 객체 목록
     * @author : 이성준
     * @since : 1.0
     */
    @PostMapping("/api/v1/users")
    List<ReadPostAuthorInfoResponseDto> requestPostAuthorInfo(@RequestBody Set<ReadPostAuthorInfoRequestDto> requests);

    @GetMapping("/api/v1/check/{userNo}")
    IsExistsUserResponseDto isExistUser(@PathVariable Long userNo);
}
