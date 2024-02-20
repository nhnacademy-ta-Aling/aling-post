package kr.aling.post.post.dto.response;

import kr.aling.post.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시물 생성시 응답 객체입니다. <br>
 * 게시물이 정상적으로 생성되어있을 때, 해당 게시물 객체를 반환 합니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class CreatePostResponseDto {

    private Post post;
}
