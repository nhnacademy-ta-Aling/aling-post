package kr.aling.post.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시물 조회시 응답 객체입니다. 게시물 번호, 내용, 작성시간, 수정시간을 반환합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@Builder
@Getter
public class ReadPostResponseDto {

    private Long postNo;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifyAt;
}
