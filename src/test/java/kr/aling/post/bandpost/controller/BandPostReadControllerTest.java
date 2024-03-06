package kr.aling.post.bandpost.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.bandpost.dto.response.BandPostDto;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.GetBandResponseDto;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeReadService;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.reply.dto.response.ReadWriterResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BandPostReadController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostReadControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BandPostFacadeReadService bandPostFacadeReadService;

    String url = "/api/v1";

    GetBandResponseDto getBandResponseDto;
    ReadWriterResponseDto writerDto;
    BandPostExceptFileQueryDto bandPostExceptFileQueryDto;
    GetFileInfoResponseDto getFileInfoResponseDto;

    BandPostDto postDto;

    @BeforeEach
    void setUp() {
        writerDto = new ReadWriterResponseDto();
        ReflectionTestUtils.setField(writerDto, "userNo", 1L);
        ReflectionTestUtils.setField(writerDto, "username", "사용자 이름");
        ReflectionTestUtils.setField(writerDto, "profilePath",
                "https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_c20e3b10d61749a2a52346ed0261d79e/aling-dev/POST-ATTACH/YTwM6I4WrOeO7z9rYiMW-uCNYPQgdCtNCeMAyaKic.jpeg");

        bandPostExceptFileQueryDto =
                new BandPostExceptFileQueryDto(1L, "제목", "내용", 1L, LocalDateTime.now(), null, false, null, true);

        getFileInfoResponseDto = new GetFileInfoResponseDto();
        ReflectionTestUtils.setField(getFileInfoResponseDto, "fileNo", 1L);
        ReflectionTestUtils.setField(getFileInfoResponseDto, "categoryNo", 1);
        ReflectionTestUtils.setField(getFileInfoResponseDto, "categoryName", "category 이름");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "path",
                "https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_c20e3b10d61749a2a52346ed0261d79e/aling-dev/POST-ATTACH/YTwM6I4WrOeO7z9rYiMW-uCNYPQgdCtNCeMAyaKic.jpeg");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "originName", "사진.jpeg");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "fileSize", "300KB");

        postDto = new BandPostDto(bandPostExceptFileQueryDto, List.of(getFileInfoResponseDto));

        getBandResponseDto = new GetBandResponseDto(writerDto, postDto);
    }

    @Test
    @DisplayName("그룹 게시글 단건 조회 API 테스트")
    void getBandPost_api_test() throws Exception {
        // given

        // when
        when(bandPostFacadeReadService.getBandPost(anyLong())).thenReturn(getBandResponseDto);

        // then
        mvc.perform(RestDocumentationRequestBuilders.get(url + "/band-posts/{postNo}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("band-post-single-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호")
                        ),

                        responseFields(
                                fieldWithPath("writer.userNo").description("작성자 회원 번호"),
                                fieldWithPath("writer.username").description("작성자 회원 이름"),
                                fieldWithPath("writer.profilePath").description("작성자 회원 프로필 사진 위치"),

                                fieldWithPath("post.postNo").description("게시글 번호"),
                                fieldWithPath("post.title").description("게시글 제목"),
                                fieldWithPath("post.content").description("게시글 내용"),
                                fieldWithPath("post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("post.modifyAt").description("게시글 수정 시간"),
                                fieldWithPath("post.isOpen").description("게시글 공개 여부"),

                                fieldWithPath("post.file[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("post.file[].categoryNo").description("게시글 첨부 파일 분류 번호"),
                                fieldWithPath("post.file[].categoryName").description("게시글 첨부 파일 분류 이름"),
                                fieldWithPath("post.file[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("post.file[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("post.file[].fileSize").description("게시글 첨부 파일 크기")
                        )
                ));

        verify(bandPostFacadeReadService, times(1)).getBandPost(anyLong());
    }

    @Test
    @DisplayName("그룹 게시판 리스트 (페이징) 조회 API 테스트")
    void getBandPostList_paging_api_test() throws Exception {
        // given
        List<GetBandResponseDto> list = List.of(getBandResponseDto);
        PageResponseDto<GetBandResponseDto> page = new PageResponseDto<>(0, 1, 1L, list);
        Pageable pageable = PageRequest.of(0, 1);

        // when
        when(bandPostFacadeReadService.getBandPostByBand(1L, pageable)).thenReturn(page);

        // then
        mvc.perform(RestDocumentationRequestBuilders.get(url + "/bands/{bandNo}/posts", 1L)
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("band-post-paging-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("bandNo").description("그룹 번호")
                        ),

                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 번호"),
                                fieldWithPath("totalPages").description("페이지 개수"),
                                fieldWithPath("totalElements").description("전체 요소 개수"),

                                fieldWithPath("content[].writer.userNo").description("작성자 회원 번호"),
                                fieldWithPath("content[].writer.username").description("작성자 회원 이름"),
                                fieldWithPath("content[].writer.profilePath").description("작성자 회원 프로필 사진 위치"),

                                fieldWithPath("content[].post.postNo").description("게시글 번호"),
                                fieldWithPath("content[].post.title").description("게시글 제목"),
                                fieldWithPath("content[].post.content").description("게시글 내용"),
                                fieldWithPath("content[].post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("content[].post.modifyAt").description("게시글 수정 시간"),
                                fieldWithPath("content[].post.isOpen").description("게시글 공개 여부"),

                                fieldWithPath("content[].post.file[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("content[].post.file[].categoryNo").description("게시글 첨부 파일 분류 번호"),
                                fieldWithPath("content[].post.file[].categoryName").description("게시글 첨부 파일 분류 이름"),
                                fieldWithPath("content[].post.file[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("content[].post.file[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("content[].post.file[].fileSize").description("게시글 첨부 파일 크기")
                        )
                ));

        verify(bandPostFacadeReadService, times(1)).getBandPostByBand(1L, pageable);
    }
}
