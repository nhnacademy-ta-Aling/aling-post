package kr.aling.post.reply.repo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import kr.aling.post.config.JpaConfig;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 댓글 관리 레포지토리 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */
@Import(JpaConfig.class)
@DataJpaTest
class ReplyManageRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReplyManageRepository replyManageRepository;

    Post post;
    Reply reply;

    @BeforeEach
    void setUp() {
        post = PostDummy.dummyPost();
        entityManager.persist(post);

        reply = ReplyDummy.dummyReply(post.getPostNo());
    }

    @Test
    @DisplayName("댓글 작성")
    void createReply() {
        Reply actual = replyManageRepository.saveAndFlush(reply);

        assertThat(actual.getReplyNo(), not(nullValue()));
        assertThat(actual.getContent(), equalTo(reply.getContent()));
        assertThat(actual.getModifyAt(), equalTo(actual.getCreateAt()));
    }

    @Test
    @DisplayName("존재하지 않는 댓글에 대한 대댓글 작성시")
    void createReplyAboutNonExistedReply() {
        Long anotherUserNo = 999L;
        Long notExistedReplyNo = 999L;

        Reply reReply = Reply.builder()
                .parentReplyNo(notExistedReplyNo)
                .content("존재하지 않는 댓글에 대한 댓글 내용")
                .postNo(reply.getPostNo())
                .userNo(anotherUserNo)
                .build();

        assertThrows(DataIntegrityViolationException.class, ()-> replyManageRepository.saveAndFlush(reReply));

    }
    @Test
    @DisplayName("존재하지 않는 게시물에 대한 댓글 작성시")
    void createReplyAboutNonExistedPost() {
        Long userNo = reply.getUserNo();
        Long notExistedPostNo = 999L;

        Reply reReply = Reply.builder()
                .parentReplyNo(null)
                .content("존재하지 않는 게시물에 대한 댓글 내용")
                .postNo(notExistedPostNo)
                .userNo(userNo)
                .build();

        assertThrows(DataIntegrityViolationException.class, ()-> replyManageRepository.saveAndFlush(reReply));

    }

    @Test
    @DisplayName("댓글 수정")
    void modifyReply() {
        Reply beforeModify = replyManageRepository.saveAndFlush(reply);

        Long replyNo = beforeModify.getReplyNo();

        String replaceContent = "수정된 댓글 내용";
        beforeModify.modifyContent(replaceContent);

        Reply actual = entityManager.find(Reply.class, replyNo);

        assertThat(actual, not(nullValue()));
        assertThat(actual.getModifyAt(), not(nullValue()));
        assertThat(actual.getContent(), equalTo(replaceContent));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteReply() {
        Long replyNo = replyManageRepository.save(reply).getReplyNo();

        boolean beforeDelete = entityManager.find(Reply.class, replyNo).getIsDelete();

        reply.softDelete();

        boolean actual = entityManager.find(Reply.class, replyNo).getIsDelete();

        assertThat(beforeDelete, is(false));
        assertThat(beforeDelete, not(equalTo(actual)));
        assertThat(actual, is(true));
    }

}