package kr.aling.post.bandpost.dto.response;

import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 하나의 게시글 정보 응답 Dto. <br>
 * <ol>
 *     <li>작성자 정보</li>
 *     <li>게시글 정보</li>
 * </ol>
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public class GetBandResponseDto {

    private ReadWriterResponseDto writer;
    private BandPostDto post;
}
