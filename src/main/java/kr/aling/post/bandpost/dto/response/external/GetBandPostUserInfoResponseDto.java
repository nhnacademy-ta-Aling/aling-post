package kr.aling.post.bandpost.dto.response.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetBandPostUserInfoResponseDto {

    private Long userNo;
    private String username;
    private String profilePath;
}
