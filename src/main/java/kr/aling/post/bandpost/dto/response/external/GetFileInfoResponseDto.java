package kr.aling.post.bandpost.dto.response.external;

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
public class GetFileInfoResponseDto {

    private Long fileNo;

    private Integer categoryNo;
    private String categoryName;

    private String path;
    private String originName;
    private String fileSize;
}
