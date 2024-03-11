package kr.aling.post.post.dto.response;

import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 1개의 게시물 조회시 사용되는 응답 객체입니다.
 * 게시물, 작성자, 업로드된 파일(사진), 그룹 정보와 같은 추가정보가 포함됩니다.
 * 
 * @author : 이성준
 * @since : 1.0
 */

@Getter
@AllArgsConstructor
@Builder
public class ReadPostResponseIntegrationDto {

    private ReadPostResponseDto post;
    private ReadUserInfoResponseDto writer;
    private List<GetFileInfoResponseDto> files;
    private PostAdditionalInformationDto additional;
}
