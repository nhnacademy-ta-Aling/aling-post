package kr.aling.post.bandpost.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Some description here.
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

    @Getter
    @AllArgsConstructor
    public static class AlingFileInfo {
        private Long fileNo;
    }

}
