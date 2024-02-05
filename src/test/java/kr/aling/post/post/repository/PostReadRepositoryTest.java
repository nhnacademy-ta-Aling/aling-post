package kr.aling.post.post.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostReadRepositoryTest {

    @Autowired
    PostReadRepository postReadRepository;

    @Autowired
    PostManageRepository postManageRepository;

    Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .content("게시글 내용")
                .isOpen(true)
                .build();

        post = postManageRepository.save(post);
    }

    @Test
    void readPost() {

        Optional<Post> postOptional = postReadRepository.findById(post.getPostNo());

        assertTrue(postOptional.isPresent());
    }
}