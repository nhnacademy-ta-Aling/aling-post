package kr.aling.post.reply.repo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import kr.aling.post.common.config.JpaConfig;
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

/**
 * 댓글 조회 레포지토리 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */

@Import(JpaConfig.class)
@DataJpaTest
class ReplyReadRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReplyReadRepository replyReadRepository;

    Post post;

    @BeforeEach
    void setUp() {
        post = PostDummy.dummyPost();

        entityManager.persist(post);
    }

    @Test
    @DisplayName("게시물 조회")
    void findById() {
        Reply reply = ReplyDummy.dummyReply(post.getPostNo());
        entityManager.persist(reply);

        Optional<Reply> replyOptional = replyReadRepository.findById(reply.getReplyNo());
        assertThat(replyOptional.isPresent(), is(true));
        Reply actual = replyOptional.get();
        assertThat(actual.getContent(), equalTo(reply.getContent()));
    }

    @Test
    @DisplayName("게시물 번호로 해당 게시물의 댓글 조회")
    void findAllByPostNo() {
        int countsReply = 10;
        for (int i = 0; i < countsReply; i++) {
            Reply reply = ReplyDummy.dummyReply(post.getPostNo());
            entityManager.persist(reply);
        }

        List<Reply> actual = replyReadRepository.findAllByPostNo(post.getPostNo());

        assertThat(actual.size(), equalTo(countsReply));
        actual.forEach(
                r -> {
                    Set<Reply> set = new HashSet<>();
                    assertThat(set.add(r), is(true));
                }
        );
    }

    @Test
    @DisplayName("게시물 번호로 게시물의 삭제되지 않은 댓글 조회")
    void findRepliesByPostNoAndIsDeleteIsFalse() {
        int countsReply = 10;
        boolean delete = false;

        String deleteMessage = "삭제된 메시지입니다.";
        for (int i = 0; i < countsReply; i++, delete = !delete) {
            Reply reply = ReplyDummy.dummyReply(post.getPostNo());
            entityManager.persist(reply);

            if (delete) {
                reply.softDelete();
                reply.modifyContent(deleteMessage);
            }
        }

        List<Reply> actual = replyReadRepository.findRepliesByPostNoAndIsDeleteIsFalse(post.getPostNo());

        assertThat(actual, is(not(empty())));
        actual.forEach(reply -> {
            assertThat(reply.getIsDelete(), is(false));
            assertThat(reply.getContent(), not(equalTo(deleteMessage)));
        });

    }
}