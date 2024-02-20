package kr.aling.post.post.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;
import kr.aling.post.config.JpaConfig;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 * 게시물 관리 레포지토리 테스트
 * 
 * @author : 이성준
 * @since : 1.0
 */
@DataJpaTest
@Import(JpaConfig.class)
class PostManageRepositoryTest {

    @Autowired
    PostManageRepository postManageRepository;

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .content("게시글 내용")
                .isOpen(true)
                .build();
    }

    @Test
    @DisplayName("테스트 환경 설정 확인")
    void loadTestConfiguration() {
        assertTrue(true);
    }

    @Test
    @DisplayName("게시물 저장")
    void save() {
        long beforeSize = postManageRepository.count();

        postManageRepository.save(post);

        long afterSize = postManageRepository.count();

        assertEquals(beforeSize + 1, afterSize);
    }

    @Test
    @DisplayName("게시물 수정")
    void modify() {
        post = postManageRepository.save(post);

        boolean beforeMakingPrivate = post.getIsOpen();

        post.switchVisibility();

        AtomicBoolean afterMakingPrivate = new AtomicBoolean(beforeMakingPrivate);
        postManageRepository.findById(post.getPostNo()).ifPresent( o -> afterMakingPrivate.set(o.getIsOpen()));

        assertAll(
                "비공개 처리 전과 후 값 비교",
                () -> assertTrue(beforeMakingPrivate),
                () -> assertThat(beforeMakingPrivate, not(equalTo(afterMakingPrivate.get()))),
                () -> assertFalse(afterMakingPrivate.get())
        );
    }

    @Test
    @DisplayName("게시물 삭제")
    void delete() {
        post = postManageRepository.save(post);

        long postNo = post.getPostNo();

        boolean beforeDelete = postManageRepository.existsById(postNo);

        postManageRepository.deleteById(postNo);

        boolean afterDelete = postManageRepository.existsById(postNo);

        assertAll(
                "게시글 삭제 전 후 존재 확인",
                () -> assertTrue(beforeDelete),
                () -> assertThat(beforeDelete, not(equalTo(afterDelete))),
                () -> assertFalse(afterDelete)
        );
    }
}