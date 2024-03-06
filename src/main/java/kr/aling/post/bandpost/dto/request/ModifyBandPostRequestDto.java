package kr.aling.post.bandpost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 게시글 수정 요청 Dto.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@NoArgsConstructor
public class ModifyBandPostRequestDto {

    @NotBlank
    @Size(max = 50)
    private String bandPostTitle;

    @NotBlank
    @Size(max = 10_000)
    private String bandPostContent;

    @NotNull
    private Long bandPostTypeNo;

}
