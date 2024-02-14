package kr.aling.post.normalpost.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 일반 게시물 관리 컨트롤러 테스트
 *
 * @author : 이성준
 * @since : 1.0
 */
@WebMvcTest(NormalPostManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class NormalPostManageControllerTest {

    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "content-type";
    public static final String CONTENT_TYPE_DESCRIPTION = "보내는 데이터의 포맷";
    public static final String ACCEPT_DESCRIPTION = "응답 받을 데이터 형식에 대한 요청 포맷";
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

        given(normalPostManageService.createNormalPost(any(), any(CreateNormalPostRequestDto.class))).willReturn(createNormalPostResponse);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(createNormalPostResponse)))
                .andDo(print())
                .andDo(document("create-normal-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("content").description("작성할 게시물 내용"),
                                fieldWithPath("isOpen").description("게시물 공개 여부")
                        ),
                        responseFields(
                                fieldWithPath("postNo").description("생성된 게시물의 번호")
                        )
                ));
    }


    @Test
    @DisplayName("일반 게시물 생성 요청시 지원하지 않는 content-type 포맷")
    void createNormalPostNotSupportedContentTypeHeader() throws Exception{
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
                .andDo(print())
                .andDo(document("create-normal-post-unsupported-content-type-format",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("content").description("작성할 게시물 내용"),
                                fieldWithPath("isOpen").description("게시물 공개 여부")
                        )
                ));
    }

    @Test
    @DisplayName("일반 게시물 생성 요청시 지원하지 않는 응답 포맷")
    void createNormalPostNotSupportedAcceptHeader() throws Exception{
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
                .andDo(print())
                .andDo(document("create-normal-post-not-acceptable-format",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("content").description("작성할 게시물 내용"),
                                fieldWithPath("isOpen").description("게시물 공개 여부")
                        )
                ));
    }

    @Test
    @DisplayName("일반 게시물 생성 요청시 요청 값이 유효하지 않음")
    void createNormalPostInvalidRequest() throws Exception{
        Long userNo = 1L;
        Long postNo = 1L;

        CreateNormalPostRequestDto createNormalPostRequest = new CreateNormalPostRequestDto();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        CreateNormalPostResponseDto createNormalPostResponse = new CreateNormalPostResponseDto(postNo);

        given(normalPostManageService.createNormalPost(any(), any(CreateNormalPostRequestDto.class))).willReturn(createNormalPostResponse);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("create-normal-post-invalid-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        )
                ));
    }



    @Test
    @DisplayName("일반 게시물 수정")
    void modifyNormalPost() throws Exception {
        Long postNo = 1L;

        ModifyNormalPostRequestDto modifyNormalPostRequest = new ModifyNormalPostRequestDto();

        ReflectionTestUtils.setField(modifyNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(modifyNormalPostRequest, "isOpen", false);

        doNothing().when(normalPostManageService).modifyNormalPost(any(),any(ModifyNormalPostRequestDto.class));

        mockMvc.perform(put(mappedUrl + "/" + postNo)
                        .content(mapper.writeValueAsString(modifyNormalPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("modify-normal-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정할 게시물 내용"),
                                fieldWithPath("isOpen").description("수정할 게시물의 공개 여부")
                        )
                ));

        then(normalPostManageService).should(times(1)).modifyNormalPost(any(),any(ModifyNormalPostRequestDto.class));
    }

    @Test
    @DisplayName("일반 게시물 삭제")
    void deleteNormalPost() throws Exception {
        Long postNo = 1L;

        mockMvc.perform(delete(mappedUrl + "/" + postNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("delete-normal-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        )
                ));
    }
}