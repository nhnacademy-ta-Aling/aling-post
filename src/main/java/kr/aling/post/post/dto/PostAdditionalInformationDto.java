package kr.aling.post.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시글 추가 정보가 담긴 DTO
 * 그룹 게시물일 경우, 해당 그룹의 식별 정보가 포함됩니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class PostAdditionalInformationDto {

    @Setter
    private Long bandNo;

    public PostAdditionalInformationDto(Long bandNo) {
        this.bandNo = bandNo;
    }

}
