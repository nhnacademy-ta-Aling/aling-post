package kr.aling.post.normalpost.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import javax.persistence.EntityManager;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class NormalPostReadRepositoryTest {

    @Autowired
    NormalPostReadRepository normalPostReadRepository;

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

        normalPostManageRepository.save(normalPost);
    }

    @Test
    void readNormalPost() {
        Optional<NormalPost> normalPostOptional = normalPostReadRepository.findById(normalPost.getPostNo());

        assertTrue(normalPostOptional.isPresent());
    }
}