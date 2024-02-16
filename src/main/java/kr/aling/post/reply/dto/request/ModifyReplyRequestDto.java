package kr.aling.post.reply.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 수정시 요청 객체입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class ModifyReplyRequestDto {

    @NotBlank
    private String content;
}
