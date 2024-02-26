package kr.aling.post.reply.dummy;

import java.time.LocalDateTime;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.entity.Reply;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 테스트용 댓글 더미 객체
 *
 * @author : 이성준
 * @since 1.0
 */
public class ReplyDummy {

    /**
     * 제약 조건 문제로 더미 객체를 생성할 때, 게시글 번호를 할당 받습니다.
     *
     * @param postNo 댓글이 달릴 게시물 번호
     * @return 댓글 더미 객체
     * @since 1.0
     */
    public static Reply dummyReply(Long postNo) {
        Long userNo = 1L;

        return Reply.builder()
                .postNo(postNo)
                .userNo(userNo)
                .content("테스트용 더미 댓글 내용")
                .parentReplyNo(null)
                .build();
    }

    public static CreateReplyRequestDto dummyCreateRequest() {
        Reply reply = dummyReply(1L);

        CreateReplyRequestDto request = new CreateReplyRequestDto();

        ReflectionTestUtils.setField(request,"parentReplyNo",reply.getParentReplyNo());
        ReflectionTestUtils.setField(request,"userNo",reply.getUserNo());
        ReflectionTestUtils.setField(request,"content",reply.getContent());

        return request;
    }

    public static CreateReplyResponseDto dummyCreateResponse() {
        Reply reply = dummyReply(1L);

        return new CreateReplyResponseDto(
                reply.getReplyNo(),
                reply.getParentReplyNo(),
                reply.getUserNo(),
                reply.getPostNo(),
                reply.getContent(),
                LocalDateTime.now()
        );
    }

    public static ModifyReplyRequestDto dummyModifyRequest() {
        Reply reply = dummyReply(1L);

        ModifyReplyRequestDto request = new ModifyReplyRequestDto();

        ReflectionTestUtils.setField(request, "content", "테스트 수정할 내용");

        return request;
    }

    public static ModifyReplyResponseDto dummyModifyResponse() {
        Reply reply = dummyReply(1L);

        return new ModifyReplyResponseDto(
                reply.getReplyNo(),
                reply.getContent(),
                LocalDateTime.now()
        );
    }
}
