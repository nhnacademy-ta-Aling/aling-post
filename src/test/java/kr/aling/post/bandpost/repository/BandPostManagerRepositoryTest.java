package kr.aling.post.bandpost.repository;

import static org.assertj.core.api.Assertions.assertThat;

import kr.aling.post.bandpost.dummy.BandPostDummy;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.repository.PostManageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class BandPostManagerRepositoryTest {

    @Autowired
    BandPostManageRepository bandPostManageRepository;

    @Autowired
    PostManageRepository postManageRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Post post;
    BandPostType bandPostType;

    @BeforeEach
    void setUp() {
        post = PostDummy.postDummy();
        bandPostType = BandPostTypeDummy.bandPostTypeDummy();

        testEntityManager.persist(post);
        testEntityManager.persist(bandPostType);
    }

    @Test
    @DisplayName("bandPost save test")
    void bandPost_save_test() {
        // given
        BandPost bandPost = BandPostDummy.bandPostDummy(post, bandPostType);

        // when
        BandPost save = bandPostManageRepository.save(bandPost);

        // then
        assertThat(save).isNotNull();
        assertThat(save.getPostNo()).isEqualTo(post.getPostNo());
        assertThat(save.getPost().getPostNo()).isEqualTo(post.getPostNo());
        assertThat(save.getBandPostType().getBandPostTypeNo()).isEqualTo(bandPostType.getBandPostTypeNo());
        assertThat(save.getBandUserNo()).isEqualTo(bandPost.getBandUserNo());
        assertThat(save.getTitle()).isEqualTo(bandPost.getTitle());
    }

}