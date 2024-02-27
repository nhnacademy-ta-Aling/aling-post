package kr.aling.post.common.feign.client;

import kr.aling.post.bandpost.dto.response.external.GetBandPostUserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
