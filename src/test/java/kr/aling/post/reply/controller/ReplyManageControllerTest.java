package kr.aling.post.reply.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import kr.aling.post.reply.dto.request.CreateReplyRequestDto;
import kr.aling.post.reply.dto.request.ModifyReplyRequestDto;
import kr.aling.post.reply.dto.response.CreateReplyResponseDto;
import kr.aling.post.reply.dto.response.ModifyReplyResponseDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.service.ReplyManageService;
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
 * @since : 1.0
 */

@WebMvcTest(ReplyManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class ReplyManageControllerTest {
    String mappedUrl = "/api/v1/replies";
    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "content-type";
    public static final String CONTENT_TYPE_DESCRIPTION = "보내는 데이터의 포맷";
    public static final String ACCEPT_DESCRIPTION = "응답 받을 데이터 형식에 대한 요청 포맷";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ReplyManageService replyManageService;

    Reply reply;

    @BeforeEach
    void setUp() {
        reply = ReplyDummy.dummyReply(1L);
    }

    @Test
    @DisplayName("댓글 작성")
    void createReply() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        CreateReplyResponseDto response = ReplyDummy.dummyCreateResponse();

        given(replyManageService.createReply(any(CreateReplyRequestDto.class))).willReturn(response);

        mockMvc.perform(post(mappedUrl)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().bytes((mapper.writeValueAsString(response)).getBytes()))
                .andDo(print())
                .andDo(document("create-reply",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("parentReplyNo").description("대댓글인 경우 부모댓글"),
                                fieldWithPath("userNo").description("댓글 작성자 번호"),
                                fieldWithPath("postNo").description("댓글을 작성한 게시글 번호"),
                                fieldWithPath("content").description("작성할 댓글 내용")
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
    }

    @Test
    @DisplayName("댓글 수정")
    void modifyReply() throws Exception {
        Long replyNo = 1L;

        ModifyReplyRequestDto request = ReplyDummy.dummyModifyRequest();

        ModifyReplyResponseDto response = ReplyDummy.dummyModifyResponse();

        given(replyManageService.modifyReply(any(), any(ModifyReplyRequestDto.class))).willReturn(response);

        mockMvc.perform(put(mappedUrl + "/" + replyNo)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().bytes((mapper.writeValueAsString(response)).getBytes()))
                .andDo(print())
                .andDo(document("modify-reply",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("content").description("작성할 댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("replyNo").description("수정 댓글의 번호"),
                                fieldWithPath("content").description("수정한 댓글 내용"),
                                fieldWithPath("modifyAt").description("댓글 수성 시기")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 삭제")
    void safeDeleteByReplyNo() throws Exception {
        Long replyNo = 1L;

        mockMvc.perform(delete(mappedUrl + "/" + replyNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("delete-reply",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        )
                ));


    }

    @Test
    @DisplayName("댓글 생성 요청시 지원하지 않는 content-type 포맷")
    void createNormalPostNotSupportedContentTypeHeader() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        mockMvc.perform(post(mappedUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string(containsString("application/json")))
                .andDo(print())
                .andDo(document("create-reply-unsupported-content-type-format",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("parentReplyNo").description("대댓글인 경우 부모댓글"),
                                fieldWithPath("userNo").description("댓글 작성자 번호"),
                                fieldWithPath("postNo").description("댓글을 작성한 게시글 번호"),
                                fieldWithPath("content").description("작성할 댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 생성 요청시 지원하지 않는 응답 포맷")
    void createNormalPostNotSupportedAcceptHeader() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        mockMvc.perform(post(mappedUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_XML)
                        .content(mapper.writeValueAsString(request))
                )
                .andExpect(status().isNotAcceptable())
                .andDo(print())
                .andDo(document("create-reply-not-acceptable-format",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        requestFields(
                                fieldWithPath("parentReplyNo").description("대댓글인 경우 부모댓글"),
                                fieldWithPath("userNo").description("댓글 작성자 번호"),
                                fieldWithPath("postNo").description("댓글을 작성한 게시글 번호"),
                                fieldWithPath("content").description("작성할 댓글 내용")
                        )
                ));
    }

    @Test
    @DisplayName("댓글 생성 요청시 요청 값이 유효하지 않음")
    void createNormalPostInvalidRequest() throws Exception {
        CreateReplyRequestDto request = ReplyDummy.dummyCreateRequest();

        ReflectionTestUtils.setField(request, "content", "");

        mockMvc.perform(post(mappedUrl)
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("must not be blank")))
                .andDo(print())
                .andDo(document("create-reply-invalid-request",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName(ACCEPT).description(ACCEPT_DESCRIPTION),
                                headerWithName(CONTENT_TYPE).description(CONTENT_TYPE_DESCRIPTION)
                        ),
                        responseFields(
                                fieldWithPath("message").description("응답 메시지")
                        )
                ));
    }
}