package kr.aling.post.post.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 그룹 게시글 조회 응답 Dto.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class BandPostResponseDto {

    private Long bandNo;
    private Long postNo;

    private String title;
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime modifyAt;

    private Boolean isOpen;

    private Long postTypeNo;
    private String postTypeName;

}
