package kr.aling.post.bandpost.dto.response.external;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 파일 서버에 게시글 청부 파일 정보 조회 API 응답 Dto.
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
