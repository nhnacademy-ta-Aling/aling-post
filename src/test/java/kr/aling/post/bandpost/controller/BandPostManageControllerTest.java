package kr.aling.post.bandpost.controller;

import static kr.aling.post.common.utils.ConstantUtil.X_BAND_USER_NO;
import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_NO;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BandPostManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostManageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BandPostFacadeService bandPostFacadeService;

    CreateBandPostRequestDto createBandPostRequestDto;
    Long bandUserNo;
    String url = "/api/v1/band-posts";

    @BeforeEach
    void setUp() {
        createBandPostRequestDto = new CreateBandPostRequestDto();
        bandUserNo = 1L;
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 성공 테스트")
    void create_bandPost_api_success_test() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", true);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when
        doNothing().when(bandPostFacadeService).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("band-post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestHeaders(
                                headerWithName(X_BAND_USER_NO).description("그룹 회원 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),

                        requestFields(
                                fieldWithPath("bandPostTitle").description("그룹 게시글 제목")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 50자")),
                                fieldWithPath("bandPostContent").description("그룹 게시글 내용")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 10,000자")),
                                fieldWithPath("isOpen").description("게시글 공개 여부")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null")),
                                fieldWithPath("bandPostTypeNo").description("그룹 게시글 카테고리 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null")),
                                fieldWithPath("fileNoList").description("파일 번호 리스트")
                                        .attributes(key(REQUIRED).value(REQUIRED_NO))
                                        .attributes(key(VALID).value("최대 10개"))
                        )

                ));

        verify(bandPostFacadeService, times(1)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - null bandPostTitle")
    void create_bandPost_api_fail_test_null_bandPostTitle() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", null);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", true);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - 50자 초과 bandPostTitle")
    void create_bandPost_api_fail_test_over50_bandPostTitle() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "i".repeat(51));
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", true);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - null bandPostContent")
    void create_bandPost_api_fail_test_null_bandPostContent() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", null);
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", true);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - over 10_000자 bandPostContent")
    void create_bandPost_api_fail_test_over10000_bandPostContent() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "i".repeat(10_001));
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", true);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - null isOpen")
    void create_bandPost_api_fail_test_null_isOpen() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", null);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - null bandPostType")
    void create_bandPost_api_fail_test_null_bandPostType() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", false);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", null);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList", List.of(1L, 2L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }

    @Test
    @DisplayName("그룹 게시글 생성 API 실패 테스트 - over size fileNoList")
    void create_bandPost_api_fail_test_over_size_fileNoList() throws Exception {
        // given
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTitle", "title");
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostContent", "content");
        ReflectionTestUtils.setField(createBandPostRequestDto, "isOpen", false);
        ReflectionTestUtils.setField(createBandPostRequestDto, "bandPostTypeNo", 1L);
        ReflectionTestUtils.setField(createBandPostRequestDto, "fileNoList",
                List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L));

        // when

        // then
        mvc.perform(post(url)
                        .header(X_BAND_USER_NO, bandUserNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBandPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(bandPostFacadeService, times(0)).createBandPostFacade(any(CreateBandPostRequestDto.class), anyLong());
    }
}