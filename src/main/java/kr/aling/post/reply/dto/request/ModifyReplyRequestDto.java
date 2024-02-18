package kr.aling.post.reply.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 수정시 요청 객체입니다.
 * 수정할 댓글의 번호와 수정 내용이 담깁니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReplyRequestDto {

    @NotBlank
    private String content;
}
