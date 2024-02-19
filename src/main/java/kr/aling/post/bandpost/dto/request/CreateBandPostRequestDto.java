package kr.aling.post.bandpost.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 생성 요청 Dto.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@NoArgsConstructor
public class CreateBandPostRequestDto {

    @NotBlank
    @Size(max = 50)
    private String bandPostTitle;

    @NotBlank
    @Size(max = 10_000)
    private String bandPostContent;

    @NotNull
    private Boolean isOpen;

    @NotNull
    private Long bandPostTypeNo;

    @Size(max = 10)
    private List<Long> fileNoList;

}
