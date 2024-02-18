package kr.aling.post.reply.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : 이성준
 * @since : 1.0
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
    String mappedUrl = "/api/v1/replies";

    @Test
    @DisplayName("게시물 번호로 댓글 조회")
    void readNormalPost() throws Exception {
        Long postNo = 1L;
        Reply reply = ReplyDummy.dummyReply(postNo);

        Page<ReadReplyResponseDto> page = new PageImpl<>(
                List.of(ReplyUtils.convertToReadResponse(reply), ReplyUtils.convertToReadResponse(reply)));

        PageResponseDto<ReadReplyResponseDto> response = PageUtils.convert(page);

        given(replyReadService.readRepliesByPostNo(any(), any(Pageable.class))).willReturn(response);

        mockMvc.perform(get(mappedUrl)
                        .param("postNo", postNo.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        mapper.writeValueAsString(response)
                ))
                .andDo(print())
                .andDo(
                        document("read-replies-by-postNo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                        headerWithName("content-type").description("보내는 데이터의 형식")
                                ),
                                responseFields(
                                        fieldWithPath("content[].replyNo").description("댓글 번호"),
                                        fieldWithPath("content[].postNo").description("댓글이 달린 게시물 번호"),
                                        fieldWithPath("content[].parentReplyNo").description("대댓글인 경우 부모 댓글의 번호"),
                                        fieldWithPath("content[].userNo").description("댓글을 작성한 유저 번호"),
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