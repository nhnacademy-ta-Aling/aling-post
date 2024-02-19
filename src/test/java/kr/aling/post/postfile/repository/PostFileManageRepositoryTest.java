package kr.aling.post.postfile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.postfile.dummy.PostFileDummy;
import kr.aling.post.postfile.entity.PostFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostFileManageRepositoryTest {

    @Autowired
    PostFileManageRepository postFileManageRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Post post;

    @BeforeEach
    void setUp() {
        post = PostDummy.postDummy();

        testEntityManager.persist(post);
    }

    @Test
    @DisplayName("PostFile save test")
    void postFile_save_test() {
        // given
        PostFile postFile = PostFileDummy.postFileDummy(post);

        // when
        PostFile savePostFile = postFileManageRepository.save(postFile);

        // then
        assertThat(savePostFile).isNotNull();
        assertThat(savePostFile.getPostFileNo()).isNotNull();
        assertThat(savePostFile.getPost().getPostNo()).isEqualTo(postFile.getPost().getPostNo());
        assertThat(savePostFile.getFileNo()).isEqualTo(postFile.getFileNo());
    }
}