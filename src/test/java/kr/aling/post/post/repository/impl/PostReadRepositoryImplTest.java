package kr.aling.post.post.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostReadRepositoryImplTest {

    @Autowired
    private PostReadRepository postReadRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("게시물 스크랩 조회용 게시물 정보 조회 성공")
    void getPostInfoForScrap() {
        // given
        Post persistPost = testEntityManager.persist(PostDummy.dummyPost());

        // when
        List<ReadPostScrapsResponseDto> list = postReadRepository.getPostInfoForScrap(List.of(persistPost.getPostNo()));

        // then
        assertThat(list).isNotNull();
        assertThat(list.get(0).getPostNo()).isEqualTo(persistPost.getPostNo());
        assertThat(list.get(0).getContent()).isEqualTo(persistPost.getContent());
        assertThat(list.get(0).getIsOpen()).isEqualTo(persistPost.getIsOpen());
        assertThat(list.get(0).getIsDelete()).isEqualTo(persistPost.getIsDelete());
    }
}