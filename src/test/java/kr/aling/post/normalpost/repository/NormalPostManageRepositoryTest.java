package kr.aling.post.normalpost.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import kr.aling.post.config.JpaConfig;
import kr.aling.post.normalpost.dummy.NormalPostDummy;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaSystemException;

/**
 * 일반 게시물 관리 레포지토리 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@Slf4j
@DataJpaTest
@Import(JpaConfig.class)
class NormalPostManageRepositoryTest {

    @Autowired
    NormalPostManageRepository normalPostManageRepository;

    @Autowired
    TestEntityManager entityManager;

    NormalPost normalPost;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .content("게시글 내용")
                .isOpen(true)
                .build();

        entityManager.persist(post);

        normalPost = NormalPost.builder()
                .post(post)
                .userNo(1L)
                .build();
    }

    @Test
    @DisplayName("테스트 환경 설정 확인")
    void loadTestConfiguration() {
        assertTrue(true);
    }

    @Test
    @DisplayName("일반 게시물 저장")
    void saveNormalPost() {
        normalPostManageRepository.save(normalPost);

        boolean actual = normalPostManageRepository.existsById(normalPost.getPostNo());

        assertTrue(actual);
    }

    @Test
    @DisplayName("존재하지 않는 게시물에 대해 일반 게시물 추가")
    void saveNormalPostAboutNotExistedPost() {
        long postNo = 2L;

        Post post = entityManager.find(Post.class, postNo);

        NormalPost normal = NormalPostDummy.normalPostDummy();

        assertNull(post);
        assertThrows(JpaSystemException.class, () -> normalPostManageRepository.saveAndFlush(normal));

    }

    @Test
    @DisplayName("일반 게시물 삭제")
    void deleteNormalPost() {
        normalPostManageRepository.save(normalPost);

        boolean beforeDelete = normalPostManageRepository.findById(normalPost.getPostNo()).get().getPost().getIsDelete();

        normalPost.getPost().softDelete();

        boolean afterDelete = normalPostManageRepository.findById(normalPost.getPostNo()).get().getPost().getIsDelete();

        assertAll("게시물 존재 여부 확인",
                () -> assertFalse(beforeDelete),
                () -> assertThat(beforeDelete, not(equalTo(afterDelete))),
                () -> assertTrue(afterDelete)
        );
    }
}