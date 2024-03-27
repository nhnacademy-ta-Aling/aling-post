package kr.aling.post.reply.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_NO;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.service.ReplyManageService;
import kr.aling.post.util.MockMvcUtils;
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

/**
 * @author : 이성준
 * @since 1.0
 */

@WebMvcTest(ReplyManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class ReplyManageControllerTest {

    public static final String X_USER_NO = "X-User-No";
    String mappedUrl = "/api/v1/posts/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ReplyManageService replyManageService;

    Reply reply;
    CreateReplyRequestDto createReplyRequestDto;

    @BeforeEach
    void setUp() {
        reply = ReplyDummy.dummyReply(1L);

        createReplyRequestDto = new CreateReplyRequestDto();

    }

    @Test
    @DisplayName("댓글 작성")
    void createReply() throws Exception {

        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        CreateReplyResponseDto response = new CreateReplyResponseDto(2L, 1L, 1L, 1L, "댓글", LocalDateTime.now());

        given(replyManageService.createReply(anyLong(), anyLong(), any(CreateReplyRequestDto.class))).willReturn(
                response);

        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                post(mappedUrl + "/{postNo}/replies/", reply.getPostNo()),
                                request
                        ))
                .andExpect(status().isCreated())
                .andExpect(content().bytes((mapper.writeValueAsString(response)).getBytes()))
                .andDo(print())
                .andDo(document("reply-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호")
                        ),

                        requestHeaders(
                                headerWithName(X_USER_NO).description("댓글 작성자 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null"))
                        ),

                        requestFields(
                                fieldWithPath("parentReplyNo").description("대댓글인 경우 부모댓글")
                                        .attributes(key(REQUIRED).value(REQUIRED_NO))
                                        .attributes(key(VALID).value("")),
                                fieldWithPath("content").description("작성할 댓글 내용")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 1,000자"))
                        ),

                        responseFields(
                                fieldWithPath("replyNo").description("생성된 댓글의 번호"),
                                fieldWithPath("parentReplyNo").description("대댓글인 경우 부모댓글"),
                                fieldWithPath("userNo").description("댓글 작성자 번호"),
                                fieldWithPath("postNo").description("댓글을 작성한 게시글 번호"),
                                fieldWithPath("content").description("작성할 댓글 내용"),
                                fieldWithPath("createAt").description("댓글 작성 시기")
                        )
                ));

        verify(replyManageService, times(1)).createReply(anyLong(), anyLong(), any(CreateReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 작성 실패 - userNo Null")
    void createReply_fail_userNo_Null() throws Exception {
        // given
        ReflectionTestUtils.setField(createReplyRequestDto, "content", "content");

        // when

        // then
        mockMvc.perform(post(mappedUrl + "/{postNo}/replies/", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createReplyRequestDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(replyManageService, times(0)).createReply(anyLong(), anyLong(), any(CreateReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 작성 실패 - content Null")
    void createReply_fail_content_null() throws Exception {
        // given
        ReflectionTestUtils.setField(createReplyRequestDto, "content", "");

        // when

        // then
        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                post(mappedUrl + "/{postNo}/replies/", 1L),
                                createReplyRequestDto
                        ))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(replyManageService, times(0)).createReply(anyLong(), anyLong(), any(CreateReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 작성 실패 - content 1000 글자 초과")
    void createReply_fail_content_over_1000() throws Exception {
        // given
        ReflectionTestUtils.setField(createReplyRequestDto, "content", "i".repeat(1_001));

        // when

        // then


        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                post(mappedUrl + "/{postNo}/replies/", 1L),
                                createReplyRequestDto)
                )
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(replyManageService, times(0)).createReply(anyLong(), anyLong(), any(CreateReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 수정 성공 테스트")
    void modifyReply() throws Exception {
        ModifyReplyRequestDto request = ReplyDummy.dummyModifyRequest();

        ModifyReplyResponseDto response = new ModifyReplyResponseDto(1L, "수정된 댓글", LocalDateTime.now());

        given(replyManageService.modifyReply(any(), any(), any(ModifyReplyRequestDto.class))).willReturn(response);

        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                put(mappedUrl + "{postNo}/replies/{replyNo}", 1L, 1L),
                                request
                        ))
                .andExpect(status().isOk())
                .andExpect(content().bytes((mapper.writeValueAsString(response)).getBytes()))
                .andDo(print())
                .andDo(document("reply-modify",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호"),
                                parameterWithName("replyNo").description("댓글 번호")
                        ),

                        requestFields(
                                fieldWithPath("content").description("작성할 댓글 내용")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 1,000자"))
                        ),

                        responseFields(
                                fieldWithPath("replyNo").description("수정 댓글의 번호"),
                                fieldWithPath("content").description("수정한 댓글 내용"),
                                fieldWithPath("modifyAt").description("댓글 수성 시기")
                        )
                ));

        verify(replyManageService, times(1)).modifyReply(anyLong(), anyLong(), any(ModifyReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 수정 실패 테스트 - content null")
    void modifyReply_fail_content_null() throws Exception {
        // given
        ModifyReplyRequestDto modifyReplyRequestDto = new ModifyReplyRequestDto();
        ReflectionTestUtils.setField(modifyReplyRequestDto, "content", null);

        // when

        // then
        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                put(mappedUrl + "/{postNo}/replies/{replyNo}", 1L, 1L),
                                modifyReplyRequestDto
                        ))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(replyManageService, times(0)).modifyReply(anyLong(), anyLong(), any(ModifyReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 수정 실패 테스트 - content 1000자 초과")
    void modifyReply_fail_content_over_1000() throws Exception {
        // given
        ModifyReplyRequestDto modifyReplyRequestDto = new ModifyReplyRequestDto();
        ReflectionTestUtils.setField(modifyReplyRequestDto, "content", "i".repeat(1_001));

        // when

        // then
        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                put(mappedUrl + "/{postNo}/replies/{replyNo}", 1L, 1L),
                                modifyReplyRequestDto
                        ))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        verify(replyManageService, times(0)).modifyReply(anyLong(), anyLong(), any(ModifyReplyRequestDto.class));
    }

    @Test
    @DisplayName("댓글 삭제")
    void safeDeleteByReplyNo() throws Exception {

        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                delete(mappedUrl + "{postNo}/replies/{replyNo}", 1L, 1L)
                        ))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("reply-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("postNo").description("게시글 번호"),
                                parameterWithName("replyNo").description("댓글 번호")
                        )
                ));

        verify(replyManageService, times(1)).safeDeleteByReplyNo(anyLong(), anyLong());
    }

    @Test
    @DisplayName("댓글 생성 요청시 지원하지 않는 content-type 포맷")
    void createNormalPostNotSupportedContentTypeHeader() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        mockMvc.perform(post(mappedUrl + reply.getPostNo() + "/replies/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string(containsString("application/json")))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 생성 요청시 지원하지 않는 응답 포맷")
    void createNormalPostNotSupportedAcceptHeader() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        mockMvc.perform(post(mappedUrl + reply.getPostNo() + "/replies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_XML)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 생성 요청시 요청 값이 유효하지 않음")
    void createNormalPostInvalidRequest() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        ReflectionTestUtils.setField(request, "content", "");

        mockMvc.perform(
                        MockMvcUtils.buildRequest(
                                post(mappedUrl + reply.getPostNo() + "/replies/"),
                                request
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("must not be blank")))
                .andDo(print());
    }



}