package kr.aling.post.postfile.adaptor.impl;

import feign.FeignException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.common.feign.client.FileFeignClient;
import kr.aling.post.postfile.adaptor.PostFileAdaptor;
import kr.aling.post.postfile.dto.request.ReadPostFileRequestDto;
import kr.aling.post.postfile.dto.response.ReadPostFileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 게시물의 파일 정보 요청 인터페이스 구현체입니다.
 * OpenFeign 을 이용하여 데이터를 요청합니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PostFileAdaptorImpl implements PostFileAdaptor {

    private final FileFeignClient fileFeignClient;

    @Override
    public Map<Long, List<GetFileInfoResponseDto>> readPostsFiles(List<ReadPostFileRequestDto> requests) {
        List<ReadPostFileResponseDto> responses = new ArrayList<>();
        try {
            responses = fileFeignClient.requestPostFiles(requests);
        } catch (FeignException e) {
            log.error(e.getMessage());
        }
        return responses.stream().collect(
                Collectors.toMap(ReadPostFileResponseDto::getPostNo, ReadPostFileResponseDto::getFiles));

    }

    @Override
    public List<GetFileInfoResponseDto> readPostFiles(List<Long> postFilesNo) {
        return fileFeignClient.requestFileInfo(postFilesNo);
    }
}
