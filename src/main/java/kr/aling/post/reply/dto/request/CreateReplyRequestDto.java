package kr.aling.post.reply.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 작성시 요청 객체입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class CreateReplyRequestDto {

    @NotNull
    private Long userNo;

    @NotNull
    private Long postNo;

    @NotBlank
    private String content;
}
