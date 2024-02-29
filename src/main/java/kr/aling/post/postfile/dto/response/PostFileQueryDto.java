package kr.aling.post.postfile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시글 첨부 파일 조회 Query Dto.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class PostFileQueryDto {

    private Long fileNo;
}
