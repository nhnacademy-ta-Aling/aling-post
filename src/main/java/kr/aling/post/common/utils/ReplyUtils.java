package kr.aling.post.common.utils;

import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Reply 관련 유틸 클래스.
 *
 * @author : 이성준
 * @since 1.0
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyUtils {

    /**
     * 엔티티를 생성 응답 객체로 변환합니다.
     *
     * @param reply 변환할 엔티티
     * @return 변환된 생성 응답 객체
     * @author : 이성준
     * @since 1.0
     */
    public static CreateReplyResponseDto convertToCreateResponse(Reply reply) {
        return new CreateReplyResponseDto(
                reply.getReplyNo(),
                reply.getParentReplyNo(),
                reply.getUserNo(),
                reply.getPostNo(),
                reply.getContent(),
                reply.getCreateAt());
    }

    /**
     * 엔티티를 조회 응답 객체로 변환합니다.
     *
     * @param reply 변환할 엔티티
     * @return 변환된 조회 응답 객체
     * @author : 이성준
     * @since 1.0
     */
    public static ReadReplyResponseDto convertToReadResponse(Reply reply) {
        return new ReadReplyResponseDto(
                reply.getReplyNo(),
                reply.getPostNo(),
                reply.getUserNo(),
                "Unknown",
                reply.getParentReplyNo(),
                reply.getContent(),
                reply.getCreateAt(),
                reply.getModifyAt()
        );
    }

    /**
     * 엔티티를 수정 응답 객체로 변환합니다.
     *
     * @param reply 변환할 엔티티
     * @return 변환된 수정 응답 객체
     * @author : 이성준
     * @since 1.0
     */
    public static ModifyReplyResponseDto convertToModifyResponse(Reply reply) {
        return new ModifyReplyResponseDto(
                reply.getReplyNo(),
                reply.getContent(),
                reply.getModifyAt()
        );
    }

    public static ReadReplyResponseDto convertToReadReplyResponse(Reply reply, String writerName) {
        return new ReadReplyResponseDto(
                reply.getReplyNo(),
                reply.getPostNo(),
                reply.getUserNo(),
                writerName,
                reply.getParentReplyNo(),
                reply.getContent(),
                reply.getCreateAt(),
                reply.getModifyAt()
        );
    }
}
