package kr.aling.post.reply.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 작성시 요청 객체입니다.
 * 부모 댓글(대댓글이라면)은 nullable 입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
public class CreateReplyRequestDto {

    private Long parentReplyNo;

    @NotNull
    private Long userNo;

    @NotBlank
    @Size(max = 1_000)
    private String content;
}
