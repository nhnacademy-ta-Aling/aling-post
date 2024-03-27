package kr.aling.post.user.adaptor.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.aling.post.common.feign.client.UserFeignClient;
import kr.aling.post.post.dto.request.ReadAuthorInfoRequestDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.adaptor.AuthorInformationAdaptor;
import kr.aling.post.user.dto.request.ReadPostAuthorInfoRequestDto;
import kr.aling.post.user.dto.response.ReadPostAuthorInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 작성자 정보 요청 인터페이스 구현체입니다. OpenFeign 을 이용하여 데이터를 요청합니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthorInformationAdaptorImpl implements AuthorInformationAdaptor {

    private final UserFeignClient userFeignClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadUserInfoResponseDto readBandPostAuthorInfo(Long bandPostUserNo) {
        return userFeignClient.requestBandPostUserInfo(bandPostUserNo);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ReadUserInfoResponseDto readNormalPostAuthorInfo(Long normalPostUserNo) {
        return userFeignClient.requestUserInfo(normalPostUserNo);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, ReadUserInfoResponseDto> readReplyAuthorInfo(Set<ReadAuthorInfoRequestDto> requests) {
        List<ReadUserInfoResponseDto> readAuthorInfoResponse =
                userFeignClient.requestUserInfos(requests.stream().map(ReadAuthorInfoRequestDto::getUserNo).collect(
                        Collectors.toList()));

        return readAuthorInfoResponse.stream().collect(
                Collectors.toMap(ReadUserInfoResponseDto::getUserNo, response -> response)
        );
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, ReadUserInfoResponseDto> readPostAuthorInfo(Set<ReadPostAuthorInfoRequestDto> requests) {
        return userFeignClient.requestPostAuthorInfo(requests).stream().collect(
                Collectors.toMap(ReadPostAuthorInfoResponseDto::getPostNo, ReadPostAuthorInfoResponseDto::getUserInfo)
        );
    }
}
