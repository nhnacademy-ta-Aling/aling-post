package kr.aling.post.bandpost.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 오직 게시글 정보만 조회 하는 QueryDto. <Br>
 * <i>(페이징 조회 하기 위해 게시글 정보만 조회)</i>
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class BandPostExceptFileQueryDto {

    private Long postNo;
    private String title;
    private String content;
    private Long bandUserNo;

    private LocalDateTime createAt;
    private LocalDateTime modifyAt;
    private Boolean isDelete;
    private String deleteReason;
    private Boolean isOpen;
}
