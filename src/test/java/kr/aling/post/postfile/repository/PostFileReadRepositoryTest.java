package kr.aling.post.postfile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.dummy.PostFileDummy;
import kr.aling.post.postfile.entity.PostFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostFileReadRepositoryTest {

    @Autowired
    PostFileReadRepository postFileReadRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Post post;

    @BeforeEach
    void setUp() {
        post = PostDummy.postDummy();
    }

    @Test
    @DisplayName("게시글의 파일 정보 조회 테스트")
    void postFiles_get_test() {
        // given
        Post savePost = testEntityManager.persist(post);

        PostFile postFile = PostFileDummy.postFileDummy(post);
        testEntityManager.persist(postFile);

        // when
        List<PostFileQueryDto> result = postFileReadRepository.getPostFileByPostNo(savePost.getPostNo());

        // then
        assertThat(result.get(0).getFileNo()).isEqualTo(postFile.getFileNo());
    }

}