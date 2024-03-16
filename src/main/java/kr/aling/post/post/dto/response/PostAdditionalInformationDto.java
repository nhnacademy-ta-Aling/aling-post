package kr.aling.post.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 게시글 추가 정보가 담긴 DTO 현재 그룹 게시물일 경우, 해당 그룹의 식별 정보를 포함하기 위해 사용합니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostAdditionalInformationDto {

    @Setter
    private Long bandNo;

}
