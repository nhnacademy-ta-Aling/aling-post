package kr.aling.post.reply.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.common.utils.ReplyUtils;
import kr.aling.post.reply.dto.response.ReadReplyResponseDto;
import kr.aling.post.reply.dummy.ReplyDummy;
import kr.aling.post.reply.entity.Reply;
import kr.aling.post.reply.service.ReplyReadService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : 이성준
 * @since 1.0
 */

@WebMvcTest(ReplyReadController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class ReplyReadControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ReplyReadService replyReadService;
    String mappedUrl = "/api/v1/posts/";

    @Test
    @DisplayName("게시물 번호로 댓글 조회")
    void readRepliesByPostNo() throws Exception {
        Long postNo = 1L;
        Reply reply = ReplyDummy.dummyReply(postNo);

        Page<ReadReplyResponseDto> page = new PageImpl<>(
                List.of(ReplyUtils.convertToReadResponse(reply)));

        PageResponseDto<ReadReplyResponseDto> response = PageUtils.convert(page);

        given(replyReadService.readRepliesByPostNo(any(), any(Pageable.class))).willReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.get(mappedUrl + "{postNo}/replies/", 1L)
                        .param("page", "1")
                        .param("size", "20")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        mapper.writeValueAsString(response)
                ))
                .andDo(print())
                .andDo(
                        document("reply-get-by-postNo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),

                                pathParameters(
                                        parameterWithName("postNo").description("게시글 번호")
                                ),

                                requestParameters(
                                        parameterWithName("page").description("페이지 번호")
                                                .attributes(key(REQUIRED).value(REQUIRED_YES)),
                                        parameterWithName("size").description("페이지 사이즈")
                                                .attributes(key(REQUIRED).value(REQUIRED_YES))
                                ),

                                responseFields(
                                        fieldWithPath("content[].replyNo").description("댓글 번호"),
                                        fieldWithPath("content[].postNo").description("댓글이 달린 게시물 번호"),
                                        fieldWithPath("content[].parentReplyNo").description("대댓글인 경우 부모 댓글의 번호"),
                                        fieldWithPath("content[].userNo").description("댓글을 작성한 유저 번호"),
                                        fieldWithPath("content[].userName").description("댓글을 작성한 유저 이름"),
                                        fieldWithPath("content[].content").description("게시물의 내용"),
                                        fieldWithPath("content[].createAt").description("최초 작성 시간"),
                                        fieldWithPath("content[].modifyAt").description("마지막 수정 시간"),
                                        fieldWithPath("pageNumber").description("현재 페이지 번호"),
                                        fieldWithPath("totalPages").description("전체 페이지 갯수"),
                                        fieldWithPath("totalElements").description("전체 요소 갯수")
                                )
                        ));
    }

}