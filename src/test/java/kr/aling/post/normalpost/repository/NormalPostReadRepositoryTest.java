package kr.aling.post.normalpost.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import kr.aling.post.config.JpaConfig;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * 일반 게시물 조회 레포지토리 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */
@DataJpaTest
@Import(JpaConfig.class)
class NormalPostReadRepositoryTest {

    @Autowired
    NormalPostReadRepository normalPostReadRepository;

    @Autowired
    TestEntityManager entityManager;

    NormalPost normalPost;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .content("게시글 내용")
                .isOpen(true)
                .build();

        post = entityManager.persist(post);

        normalPost = NormalPost.builder()
                .postNo(post.getPostNo())
                .userNo(1L)
                .build();

        entityManager.persist(normalPost);
    }

    @Test
    @DisplayName("일반 게시물 조회")
    void readNormalPost() {
        Optional<NormalPost> normalPostOptional = normalPostReadRepository.findById(normalPost.getPostNo());

        assertTrue(normalPostOptional.isPresent());
        assertThat(normalPost.getUserNo(), equalTo(normalPostOptional.get().getUserNo()));
    }
}