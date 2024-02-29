package kr.aling.post.bandpost.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 단건 게시글 조회 QueryDto. <Br>
 * <i>(쿼리 하나로 파일 번호 리스트 까지 조회)</i>
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class BandPostQueryDto {

    private Long postNo;
    private String title;
    private String content;
    private Long bandUserNo;

    private LocalDateTime createAt;
    private LocalDateTime modifyAt;
    private Boolean isDelete;
    private String deleteReason;
    private Boolean isOpen;
    private List<AlingFileInfo> files;

    /**
     * 첨부 파일 번호.
     */
    @Getter
    @AllArgsConstructor
    public static class AlingFileInfo {
        private Long fileNo;
    }

}
