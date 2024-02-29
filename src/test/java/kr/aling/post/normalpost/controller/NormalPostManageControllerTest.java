package kr.aling.post.normalpost.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;
import kr.aling.post.normalpost.service.NormalPostManageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 일반 게시물 관리 컨트롤러 테스트
 *
 * @author : 이성준
 * @since 1.0
 */
@WebMvcTest(NormalPostManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class NormalPostManageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    NormalPostManageService normalPostManageService;
    String mappedUrl = "/api/v1/normal-posts";

    @Test
    @DisplayName("일반 게시물 생성")
    void createNormalPost() throws Exception {
        Long userNo = 1L;
        Long postNo = 1L;

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        CreateNormalPostResponseDto createNormalPostResponse = new CreateNormalPostResponseDto(postNo);

        given(normalPostManageService.createNormalPost(any(), any(CreateNormalPostRequestDto.class))).willReturn(
                createNormalPostResponse);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(createNormalPostResponse)))
                .andDo(print())
                .andDo(document("normal-post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestParameters(
                                parameterWithName("userNo").description("유저 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),

                        requestFields(
                                fieldWithPath("content").description("작성할 게시물 내용")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 10,000자")),
                                fieldWithPath("isOpen").description("게시물 공개 여부")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null"))
                        ),

                        responseFields(
                                fieldWithPath("postNo").description("생성된 게시물의 번호")
                        )
                ));
    }


    @Test
    @DisplayName("일반 게시물 생성 요청시 지원하지 않는 content-type 포맷")
    void createNormalPostNotSupportedContentTypeHeader() throws Exception {
        Long userNo = 1L;

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                )
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(
                        result ->
                        {
                            String content = result.getResponse().getContentAsString();
                            assertThat(content, containsString("application/json"));
                        })
                .andDo(print());
    }

    //FIXME Controller Advice 에서 반환되는 메시지가 표시되지 않음.
    @Test
    @DisplayName("일반 게시물 생성 요청시 지원하지 않는 응답 포맷")
    void createNormalPostNotSupportedAcceptHeader() throws Exception {
        Long userNo = 1L;

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_XML)
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                )
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }

    @Test
    @DisplayName("일반 게시물 생성 요청시 요청 값이 유효하지 않음")
    void createNormalPostInvalidRequest() throws Exception {
        Long userNo = 1L;
        Long postNo = 1L;

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        CreateNormalPostResponseDto createNormalPostResponse = new CreateNormalPostResponseDto(postNo);

        given(normalPostManageService.createNormalPost(any(), any(CreateNormalPostRequestDto.class))).willReturn(
                createNormalPostResponse);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        result ->
                        {
                            String content = result.getResponse().getContentAsString();
                            assertThat(content, containsString("must not be blank"));
                        })
                .andDo(print());
    }

    @Test
    @DisplayName("일반 게시글 생성 요청 실패 테스트 - content 10,000자 넘음")
    void createNormalPost_content_over_10000() throws Exception {
        // given
        CreateNormalPostRequestDto createNormalPostRequestDto = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequestDto, "content", "i".repeat(10_001));
        ReflectionTestUtils.setField(createNormalPostRequestDto, "isOpen", true);

        // when

        // then
        mockMvc.perform(post(mappedUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createNormalPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(normalPostManageService, times(0))
                .createNormalPost(anyLong(), any(CreateNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시글 생성 요청 실패 테스트 - isOpen null")
    void createNormalPost_isOpen_null() throws Exception {
        // given
        CreateNormalPostRequestDto createNormalPostRequestDto = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequestDto, "content", "content");
        ReflectionTestUtils.setField(createNormalPostRequestDto, "isOpen", null);

        // when

        // then
        mockMvc.perform(post(mappedUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createNormalPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(normalPostManageService, times(0))
                .createNormalPost(anyLong(), any(CreateNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시물 수정")
    void modifyNormalPost() throws Exception {
        Long postNo = 1L;

        ModifyNormalPostRequestDto modifyNormalPostRequest = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequest, "content", "수정 일반 게시물 내용");
        ReflectionTestUtils.setField(modifyNormalPostRequest, "isOpen", false);

        doNothing().when(normalPostManageService).modifyNormalPost(any(), any(ModifyNormalPostRequestDto.class));

        mockMvc.perform(RestDocumentationRequestBuilders.put(mappedUrl + "/{postNo}", postNo)
                        .content(mapper.writeValueAsString(modifyNormalPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andDo(document("normal-post-modify",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),

                        requestFields(
                                fieldWithPath("content").description("수정할 게시물 내용")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 10,000자")),
                                fieldWithPath("isOpen").description("수정할 게시물의 공개 여부")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null"))
                        )
                ));

        then(normalPostManageService).should(times(1)).modifyNormalPost(any(), any(ModifyNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시글 수정 실패 - content null")
    void modifyNormalPost_fail_content_null() throws Exception {
        // given
        ModifyNormalPostRequestDto modifyNormalPostRequestDto = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "content", null);
        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "isOpen", true);

        // when

        // then
        mockMvc.perform(put(mappedUrl + "/{postNo}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(modifyNormalPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(normalPostManageService, times(0)).modifyNormalPost(anyLong(), any(ModifyNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시글 수정 실패 - content over 10,000 글자")
    void modifyNormalPost_fail_content_over_10000() throws Exception {
        // given
        ModifyNormalPostRequestDto modifyNormalPostRequestDto = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "content", "i".repeat(10_001));
        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "isOpen", true);

        // when

        // then
        mockMvc.perform(put(mappedUrl + "/{postNo}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(modifyNormalPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(normalPostManageService, times(0)).modifyNormalPost(anyLong(), any(ModifyNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시글 수정 실패 - isOpen null")
    void modifyNormalPost_fail_isOpen_null() throws Exception {
        // given
        ModifyNormalPostRequestDto modifyNormalPostRequestDto = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "content", "content");
        ReflectionTestUtils.setField(modifyNormalPostRequestDto, "isOpen", null);

        // when

        // then
        mockMvc.perform(put(mappedUrl + "/{postNo}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(modifyNormalPostRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(normalPostManageService, times(0)).modifyNormalPost(anyLong(), any(ModifyNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시물 삭제")
    void deleteNormalPost() throws Exception {
        Long postNo = 1L;

        mockMvc.perform(RestDocumentationRequestBuilders.delete(mappedUrl + "/{postNo}", postNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("normal-post-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호")
                        )
                ));
    }
}