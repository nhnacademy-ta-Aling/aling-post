package kr.aling.post.bandposttype.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 게시글 분류를 생성하기 위한 요청 dto.
 *
 * @author : 정유진
 * @since : 1.0
 **/
@Getter
@NoArgsConstructor
public class CreateBandPostTypeRequestDto {
    @NotNull
    private Long bandNo;

    @NotBlank
    @Size(max = 10)
    private String bandPostTypeName;
}
