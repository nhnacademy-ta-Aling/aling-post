package kr.aling.post.normalpost.repository;

import javax.sql.DataSource;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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

        post = entityManager.persist(post);

        normalPost = NormalPost.builder()
                .postNo(post.getPostNo())
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
    @DisplayName("일반 게시물 테이블은 수정하지 않음")
    void modifyNormalPost() {
        assertFalse(false);
    }

    @Test
    @DisplayName("일반 게시물 테이블 삭제")
    void deleteNormalPost() {
        normalPostManageRepository.save(normalPost);

        boolean beforeDelete = normalPostManageRepository.existsById(normalPost.getPostNo());

        normalPostManageRepository.deleteById(normalPost.getPostNo());

        boolean afterDelete = normalPostManageRepository.existsById(normalPost.getPostNo());

        assertAll("",
                ()-> assertTrue(beforeDelete),
                ()-> assertThat(beforeDelete, not(equalTo(afterDelete))),
                ()-> assertFalse(afterDelete)
        );
    }
}