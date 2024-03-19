package kr.aling.post.post.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import feign.FeignException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.aling.post.bandpost.dummy.BandPostDummy;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.common.utils.PostUtils;
import kr.aling.post.normalpost.dummy.NormalPostDummy;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.dto.response.BandPostResponseDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.NormalPostResponseDto;
import kr.aling.post.post.dto.response.PostAdditionalInformationDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.impl.PostReadServiceImpl;
import kr.aling.post.postfile.adaptor.PostFileAdaptor;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.dummy.PostFileDummy;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.postfile.repository.PostFileReadRepository;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import kr.aling.post.user.adaptor.AuthorInformationAdaptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 게시물 조회 서비스 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
class PostReadServiceTest {

    @Mock
    PostReadRepository postReadRepository;

    @Mock
    AuthorInformationAdaptor authorInformationAdaptor;

    @Mock
    PostFileAdaptor postFileAdaptor;

    @Mock
    PostFileReadRepository postFileReadRepository;

    @InjectMocks
    PostReadServiceImpl postReadService;

    @Test
    @DisplayName("게시물 조회시 올바른 응답 객체 반환")
    void readPostByPostNo() {

        Post post = PostDummy.postDummy();
        LocalDateTime createAt = LocalDateTime.now();

        ReflectionTestUtils.setField(post, "postNo", 1L);
        ReflectionTestUtils.setField(post, "createAt", createAt);
        ReflectionTestUtils.setField(post, "modifyAt", null);

        NormalPost normalPost = NormalPost.builder()
                .post(post)
                .build();

        ReflectionTestUtils.setField(post, "normalPost", normalPost);

        ReadUserInfoResponseDto writerResponse = new ReadUserInfoResponseDto(1L, "테스트 작성자", null);

        ReadPostResponseIntegrationDto integrationDto = ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(writerResponse)
                .additional(null)
                .build();

        given(postReadRepository.findByPostNoAndIsDeleteFalseAndIsOpenTrue(post.getPostNo())).willReturn(Optional.of(post));
        doThrow(FeignException.class).when(authorInformationAdaptor).readPostAuthorInfo(any());

        ReadPostResponseIntegrationDto actual = postReadService.readPostByPostNo(post.getPostNo());

        assertAll("게시물 내용과 응답 DTO 가 동일한지 확인",
                () -> assertThat(integrationDto.getPost().getPostNo(), equalTo(actual.getPost().getPostNo())),
                () -> assertThat(integrationDto.getPost().getContent(), equalTo(actual.getPost().getContent())),
                () -> assertThat(integrationDto.getPost().getCreateAt(), equalTo(actual.getPost().getCreateAt())),
                () -> assertThat(integrationDto.getPost().getModifyAt(), equalTo(actual.getPost().getModifyAt()))
        );
    }

    @Test
    @DisplayName("그룹 게시물 조회 성공 테스트")
    void readBandPostByPostNo() {
        // given
        Post post = PostDummy.postDummy();
        LocalDateTime createAt = LocalDateTime.now();

        ReflectionTestUtils.setField(post, "postNo", 1L);
        ReflectionTestUtils.setField(post, "createAt", createAt);
        ReflectionTestUtils.setField(post, "modifyAt", null);

        BandPostType bandPostType = BandPostTypeDummy.bandPostTypeDummy();

        BandPost bandPost = BandPostDummy.bandPostDummy(post, bandPostType);
        ReflectionTestUtils.setField(post, "bandPost", bandPost);

        ReadUserInfoResponseDto writerResponse = new ReadUserInfoResponseDto(1L, "테스트 작성자", null);
        PostAdditionalInformationDto additionalInformationDto =
                new PostAdditionalInformationDto(true, 1L, "title", 1L, "typeName");

        ReadPostResponseIntegrationDto integrationDto = ReadPostResponseIntegrationDto.builder()
                .post(PostUtils.convert(post))
                .writer(writerResponse)
                .additional(additionalInformationDto)
                .build();

        // when
        when(postReadRepository.findByPostNoAndIsDeleteFalseAndIsOpenTrue(anyLong())).thenReturn(Optional.of(post));
        when(authorInformationAdaptor.readBandPostAuthorInfo(anyLong())).thenReturn(writerResponse);

        // then
        ReadPostResponseIntegrationDto result = postReadService.readPostByPostNo(1L);

        verify(postReadRepository, times(1)).findByPostNoAndIsDeleteFalseAndIsOpenTrue(anyLong());
        verify(authorInformationAdaptor, times(1)).readBandPostAuthorInfo(anyLong());

        assertAll(
                () -> assertThat(integrationDto.getPost().getPostNo(), equalTo(result.getPost().getPostNo())),
                () -> assertThat(integrationDto.getPost().getContent(), equalTo(result.getPost().getContent())),
                () -> assertThat(integrationDto.getPost().getCreateAt(), equalTo(result.getPost().getCreateAt())),
                () -> assertThat(integrationDto.getPost().getModifyAt(), equalTo(result.getPost().getModifyAt()))
        );
    }

    @Test
    @DisplayName("존재하지 않는 게시물 번호에 대한 조회시도")
    void readPostThenThrow() {
        Long postNo = 2L;

        given(postReadRepository.findById(postNo)).willThrow(new PostNotFoundException(postNo));

        assertThrows(PostNotFoundException.class, () -> postReadService.readPostByPostNo(postNo),
                "Post Not Found : " + postNo);
    }

    @Test
    @DisplayName("게시물 존재 확인 성공")
    void isExistsPost() {
        // given
        Long postNo = 1L;

        when(postReadRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);

        // when
        IsExistsPostResponseDto result = postReadService.isExistsPost(postNo);

        // then
        assertEquals(Boolean.TRUE, result.getIsExists());
    }

    @Test
    @DisplayName("게시물 번호로 게시물 내용 조회 성공")
    void getPostsForScrap() {
        // given
        List<Long> postNos = List.of(1L, 2L, 3L);

        List<ReadPostScrapsPostResponseDto> list = List.of(
                new ReadPostScrapsPostResponseDto(1L, "1", false, true),
                new ReadPostScrapsPostResponseDto(2L, "2", true, true),
                new ReadPostScrapsPostResponseDto(3L, "3", true, false)
        );
        when(postReadRepository.getPostInfoForScrap(anyList())).thenReturn(list);

        // when
        ReadPostsForScrapResponseDto result = postReadService.getPostsForScrap(postNos);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getInfos()).isEqualTo(list);
    }

    @Test
    @DisplayName("공개된 모든 게시물 조회 테스트 - 파일 없는 상황")
    void readPosts_isOpen_test() {
        // given
        Post post = PostDummy.postDummy();

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> page = PageableExecutionUtils.getPage(List.of(post), pageable, () -> 1L);

        // when
        when(postReadRepository.getAllByIsDeleteFalseAndIsOpenTrue(pageable)).thenReturn(page);

        // then
        postReadService.readPostsThatIsOpen(pageable);

        verify(postReadRepository, times(1)).getAllByIsDeleteFalseAndIsOpenTrue(pageable);
    }

    @Test
    @DisplayName("공개된 모든 게시글 조회 테스트 - 파일 있는 상황")
    void readPosts_isOpen_fileList_test() {
        // given
        Post post = PostDummy.postDummy();
        ReflectionTestUtils.setField(post, "postNo", 1L);

        NormalPost normalPost = NormalPostDummy.dummyNormalPost(post);

        PostFile postFile = PostFileDummy.postFileDummy(post);
        ReflectionTestUtils.setField(postFile, "postFileNo", 1L);

        ReflectionTestUtils.setField(post, "postFileList", List.of(postFile));
        ReflectionTestUtils.setField(post, "normalPost", normalPost);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> page = PageableExecutionUtils.getPage(List.of(post), pageable, () -> 1L);

        // when
        when(postReadRepository.getAllByIsDeleteFalseAndIsOpenTrue(pageable)).thenReturn(page);

        // then
        postReadService.readPostsThatIsOpen(pageable);

        verify(postReadRepository, times(1)).getAllByIsDeleteFalseAndIsOpenTrue(pageable);
    }

    @Test
    @DisplayName("회원 별 일반 게시글 조회 테스트")
    void getNormalPosts_byUserNo_test() {
        // given
        NormalPostResponseDto normalPostResponseDto =
                new NormalPostResponseDto(1L, "content", LocalDateTime.now(), null, true);

        Pageable pageable = PageRequest.of(0, 10);
        Page<NormalPostResponseDto> page =
                PageableExecutionUtils.getPage(List.of(normalPostResponseDto), pageable, () -> 1L);

        PostFileQueryDto postFileQueryDto = new PostFileQueryDto(1L);

        // when
        when(postReadRepository.getNormalPostsByUserNo(1L, pageable)).thenReturn(page);
        when(postFileReadRepository.getPostFileByPostNo(anyLong())).thenReturn(List.of(postFileQueryDto));

        // then
        postReadService.getNormalPostsByUserNo(1L, pageable);

        verify(postReadRepository, times(1)).getNormalPostsByUserNo(1L, pageable);
        verify(postFileReadRepository, times(1)).getPostFileByPostNo(anyLong());
    }

    @Test
    @DisplayName("회원 별 그룹 게시글 조회 테스트")
    void getBandPosts_byUserNo_test() {
        // given
        BandPostResponseDto bandPostResponseDto =
                new BandPostResponseDto(1L, 1L, "title", "content", LocalDateTime.now(), null, true, 1L, "type");

        Pageable pageable = PageRequest.of(0, 10);
        Page<BandPostResponseDto> page =
                PageableExecutionUtils.getPage(List.of(bandPostResponseDto), pageable, () -> 1L);

        PostFileQueryDto postFileQueryDto = new PostFileQueryDto(1L);

        // when
        when(postReadRepository.getBandPostsByUserNo(1L, pageable)).thenReturn(page);
        when(postFileReadRepository.getPostFileByPostNo(anyLong())).thenReturn(List.of(postFileQueryDto));

        // then
        postReadService.getBandPostsByUserNo(1L, pageable);

        verify(postReadRepository, times(1)).getBandPostsByUserNo(1L, pageable);
        verify(postFileReadRepository, times(1)).getPostFileByPostNo(anyLong());
    }

}