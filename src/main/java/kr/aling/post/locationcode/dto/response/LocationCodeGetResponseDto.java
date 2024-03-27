package kr.aling.post.locationcode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 지역 코드 조회를 위한 dto.
 *
 * @author 정유진
 * @since 1.0
 **/
@AllArgsConstructor
@Getter
public class LocationCodeGetResponseDto {
    private String locationCodeNo;
    private String locationName;
}
