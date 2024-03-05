package kr.aling.post.post.dto.response;

import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.post.dto.AdditionalInformationDto;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
@AllArgsConstructor
@Builder
public class ReadPostIntegrationDto {

    private ReadPostResponseDto post;
    private ReadWriterResponseDto writer;
    private List<GetFileInfoResponseDto> file;
    private AdditionalInformationDto additional;
}
