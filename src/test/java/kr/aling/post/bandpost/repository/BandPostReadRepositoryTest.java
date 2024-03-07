package kr.aling.post.bandpost.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.dummy.BandPostDummy;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.config.JpaConfig;
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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@Import(JpaConfig.class)
class BandPostReadRepositoryTest {

    @Autowired
    BandPostReadRepository bandPostReadRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Post post;
    PostFile postFile;
    BandPostType bandPostType;

    BandPost bandPost;

    @BeforeEach
    void setUp() {
        post = PostDummy.postDummy();
        bandPostType = BandPostTypeDummy.bandPostTypeDummy();
        postFile = PostFileDummy.postFileDummy(post);
    }

    @Test
    @DisplayName("그룹 게시글 단건 조회 테스트")
    void bandPost_single_test() {
        // given
        Post savePost = testEntityManager.persist(post);
        testEntityManager.persist(bandPostType);
        testEntityManager.persist(postFile);

        BandPost bandPost = BandPostDummy.bandPostDummy(post, bandPostType);
        testEntityManager.persist(bandPost);

        // when
        BandPostQueryDto result = bandPostReadRepository.getBandPostByPostNo(savePost.getPostNo());

        // then
        assertThat(result.getPostNo()).isEqualTo(savePost.getPostNo());
        assertThat(result.getTitle()).isEqualTo(bandPost.getTitle());
        assertThat(result.getContent()).isEqualTo(post.getContent());
        assertThat(result.getBandUserNo()).isEqualTo(bandPost.getBandUserNo());
        assertThat(result.getCreateAt()).isBefore(LocalDateTime.now());
        assertThat(result.getModifyAt()).isNull();
        assertThat(result.getDeleteReason()).isEqualTo(post.getDeleteReason());
        assertThat(result.getIsOpen()).isEqualTo(post.getIsOpen());
        assertThat(result.getFiles().get(0).getFileNo()).isEqualTo(postFile.getFileNo());
    }

    @Test
    @DisplayName("그룹의 게시글 paging 조회 테스트")
    void bandPost_paging_test() {
        // given
        Post savePost = testEntityManager.persist(post);
        testEntityManager.persist(bandPostType);

        BandPost bandPost = BandPostDummy.bandPostDummy(post, bandPostType);
        testEntityManager.persist(bandPost);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<BandPostExceptFileQueryDto> result =
                bandPostReadRepository.getBandPostByBand(bandPost.getBandNo(), pageable);

        // then
        assertThat(result.getContent().get(0).getPostNo()).isEqualTo(savePost.getPostNo());
        assertThat(result.getContent().get(0).getTitle()).isEqualTo(bandPost.getTitle());
        assertThat(result.getContent().get(0).getBandUserNo()).isEqualTo(bandPost.getBandNo());
        assertThat(result.getContent().get(0).getCreateAt()).isBefore(LocalDateTime.now());
        assertThat(result.getContent().get(0).getModifyAt()).isNull();
        ;
        assertThat(result.getContent().get(0).getIsDelete()).isEqualTo(post.getIsDelete());
        assertThat(result.getContent().get(0).getDeleteReason()).isEqualTo(post.getDeleteReason());
        assertThat(result.getContent().get(0).getIsOpen()).isEqualTo(post.getIsOpen());
    }

    @Test
    @DisplayName("그룹 게시글 분류 내 그룹 게시글 개수 조회 테스트")
    void getCountBandPostByBandPostTypeNo_successTest() {
        // given
        Post persistPost = testEntityManager.persist(post);
        BandPostType persistBandPostType = testEntityManager.persist(bandPostType);
        BandPost bandPost = BandPostDummy.bandPostDummy(persistPost, persistBandPostType);
        testEntityManager.persist(bandPost);

        // when

        // then
        long count =
                bandPostReadRepository.getCountBandPostByBandPostTypeNo(bandPostType.getBandPostTypeNo());
        assertThat(count).isEqualTo(1L);
    }
}