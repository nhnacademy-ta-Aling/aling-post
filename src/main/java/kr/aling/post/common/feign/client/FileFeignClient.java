package kr.aling.post.common.feign.client;

import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.postfile.dto.request.ReadPostFileRequestDto;
import kr.aling.post.postfile.dto.response.ReadPostFileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@FeignClient("aling-file")
public interface FileFeignClient {

    @GetMapping("/api/v1/files")
    List<GetFileInfoResponseDto> requestFileInfo(@RequestParam("no") List<Long> fileNoList);

    @PostMapping("/api/v1/files/posts")
    List<ReadPostFileResponseDto> requestPostFiles(@RequestBody List<ReadPostFileRequestDto> requests);
}
