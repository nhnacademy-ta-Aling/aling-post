package kr.aling.post.common.feign.client;

import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetBandPostUserInfoResponseDto;
import kr.aling.post.post.dto.request.WriterRequestDto;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import kr.aling.post.user.dto.response.IsExistsUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@FeignClient("aling-user")
public interface UserFeignClient {

    @GetMapping("/api/v1/band-users/{bandUserNo}")
    GetBandPostUserInfoResponseDto requestBandPostUserInfo(@PathVariable("bandUserNo") Long bandUserNo);

    @PostMapping("/api/v1/users")
    List<ReadWriterResponseDto> requestWriterNames(@RequestBody WriterRequestDto request);

    @GetMapping("/api/v1/check/{userNo}")
    IsExistsUserResponseDto isExistUser(@PathVariable Long userNo);
}
