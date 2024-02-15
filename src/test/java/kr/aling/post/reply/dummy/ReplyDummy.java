package kr.aling.post.reply.dummy;

import kr.aling.post.reply.entity.Reply;

/**
 * 테스트용 댓글 더미 객체
 *
 * @author : 이성준
 * @since : 1.0
 */
public class ReplyDummy {

    /**
     * 제약 조건 문제로 더미 객체를 생성할 때, 게시글 번호를 할당 받습니다.
     *
     * @param postNo 댓글이 달릴 게시물 번호
     * @return 댓글 더미 객체
     * @author : 이성준
     * @since : 1.0
     */
    public static Reply dummyReply(Long postNo){
        Long userNo = 1L;

        return Reply.builder()
                .postNo(postNo)
                .userNo(userNo)
                .content("테스트용 더미 댓글 내용")
                .parentReplyNo(null)
                .build();
    }
}
