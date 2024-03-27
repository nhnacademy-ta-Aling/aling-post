package kr.aling.post.post.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import kr.aling.post.bandpost.dummy.BandPostDummy;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.config.JpaConfig;
import kr.aling.post.normalpost.dummy.NormalPostDummy;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.dto.response.BandPostResponseDto;
import kr.aling.post.post.dto.response.NormalPostResponseDto;
import kr.aling.post.post.entity.Post;
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

/**
 * 게시물 조회 레포지토리 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@DataJpaTest
@Import(JpaConfig.class)
class PostReadRepositoryTest {

    @Autowired
    PostReadRepository postReadRepository;

    @Autowired
    PostManageRepository postManageRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Post post;
    NormalPost normalPost;
    BandPost bandPost;
    BandPostType bandPostType;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .content("게시글 내용")
                .isOpen(true)
                .build();

        Post savePost = testEntityManager.persist(post);
        normalPost = NormalPostDummy.dummyNormalPost(savePost);

        bandPostType = BandPostTypeDummy.bandPostTypeDummy();
        testEntityManager.persist(bandPostType);

        bandPost = BandPostDummy.bandPostDummy(savePost, bandPostType);
    }

    @Test
    @DisplayName("게시물 조회")
    void readPost() {

        Optional<Post> postOptional = postReadRepository.findById(post.getPostNo());

        assertTrue(postOptional.isPresent());
        assertThat(post.getContent()).isEqualTo(postOptional.get().getContent());
    }

    @Test
    @DisplayName("회원 별 일반 게시글 작성 목록 조회 테스트")
    void get_normalPosts_by_userNo_test() {
        // given
        testEntityManager.persist(normalPost);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<NormalPostResponseDto> result =
                postReadRepository.getNormalPostsByUserNo(normalPost.getUserNo(), pageable);

        // then
        assertThat(result.getContent().get(0).getPostNo()).isEqualTo(post.getPostNo());
        assertThat(result.getContent().get(0).getContent()).isEqualTo(post.getContent());
        assertThat(result.getContent().get(0).getCreateAt()).isEqualTo(post.getCreateAt());
        assertThat(result.getContent().get(0).getModifyAt()).isEqualTo(post.getModifyAt());
        assertThat(result.getContent().get(0).getIsOpen()).isEqualTo(post.getIsOpen());
    }

    @Test
    @DisplayName("회원 별 그룹 게시글 작성 목록 조회 테스트")
    void get_bandPosts_by_userNo_test() {
        // given
        testEntityManager.persist(bandPost);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<BandPostResponseDto> result = postReadRepository.getBandPostsByUserNo(bandPost.getUserNo(), pageable);

        // then
        assertThat(result.getContent().get(0).getBandNo()).isEqualTo(bandPost.getBandNo());
        assertThat(result.getContent().get(0).getPostNo()).isEqualTo(post.getPostNo());
        assertThat(result.getContent().get(0).getTitle()).isEqualTo(bandPost.getTitle());
        assertThat(result.getContent().get(0).getContent()).isEqualTo(post.getContent());
        assertThat(result.getContent().get(0).getCreateAt()).isEqualTo(post.getCreateAt());
        assertThat(result.getContent().get(0).getModifyAt()).isEqualTo(post.getModifyAt());
        assertThat(result.getContent().get(0).getIsOpen()).isEqualTo(post.getIsOpen());
        assertThat(result.getContent().get(0).getPostTypeNo()).isEqualTo(bandPostType.getBandPostTypeNo());
        assertThat(result.getContent().get(0).getPostTypeName()).isEqualTo(bandPostType.getName());
    }

}