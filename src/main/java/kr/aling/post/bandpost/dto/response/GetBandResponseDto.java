package kr.aling.post.bandpost.dto.response;

import kr.aling.post.bandpost.dto.response.external.GetBandPostUserInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class GetBandResponseDto {

    private GetBandPostUserInfoResponseDto writer;
    private BandPostDto post;
}
