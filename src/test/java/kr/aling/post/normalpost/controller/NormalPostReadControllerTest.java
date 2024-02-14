package kr.aling.post.normalpost.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.common.utils.NormalPostUtils;
import kr.aling.post.common.utils.PageUtils;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponseDto;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.normalpost.service.NormalPostReadService;
import kr.aling.post.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 일반 게시물 조회 컨트롤러 테스트
 * @author : 이성준
 * @since : 1.0
 */
@WebMvcTest(NormalPostReadController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class NormalPostReadControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper mapper;

    @MockBean
    NormalPostReadService normalPostReadService;
    String mappedUrl = "/api/v1/normal-posts";

    @Test
    @DisplayName("게시물 번호로 일반 게시물 조회")
    void readNormalPost() throws Exception {
        long postNo = 1;
        long userNo = 1;

        Post post = Post.builder()
                .content("테스트용 내용")
                .isOpen(true)
                .build();

        ReflectionTestUtils.setField(post, "postNo", postNo);
        ReflectionTestUtils.setField(post, "createAt", LocalDateTime.now());
        ReflectionTestUtils.setField(post, "modifyAt", LocalDateTime.now());

        NormalPost normalPost = NormalPost.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        ReflectionTestUtils.setField(normalPost, "post", post);

        ReadNormalPostResponseDto response = NormalPostUtils.convert(normalPost);

        given(normalPostReadService.readNormalPostByPostNo(postNo)).willReturn(response);

        mockMvc.perform(get(mappedUrl + "/" + postNo)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        mapper.writeValueAsString(response)
                ))
                .andDo(print())
                .andDo(
                        document("read-normal-post-by-postNo",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                        headerWithName("content-type").description("보내는 데이터의 형식")
                                ),
                                responseFields(
                                        fieldWithPath("post.postNo").description("게시물 번호"),
                                        fieldWithPath("post.content").description("게시물의 내용"),
                                        fieldWithPath("post.createAt").description("최초 작성 시간"),
                                        fieldWithPath("post.modifyAt").description("마지막 수정 시간"),
                                        fieldWithPath("userNo").description("유저 번호")
                                )
                        ));
    }

    @Test
    @DisplayName("유저 번호로 일반 게시물 목록 조회")
    void readNormalPostsByUser() throws Exception {
        long userNo = 1L;
        long postNo = 1L;

        Post post = Post.builder()
                .content("테스트용 게시물 내용")
                .isOpen(true)
                .build();

        ReflectionTestUtils.setField(post, "postNo", postNo);
        ReflectionTestUtils.setField(post, "createAt", LocalDateTime.now());
        ReflectionTestUtils.setField(post, "modifyAt", LocalDateTime.now());

        NormalPost normalPost = NormalPost.builder()
                .postNo(postNo)
                .userNo(userNo)
                .build();

        ReflectionTestUtils.setField(normalPost, "post", post);

        PageResponseDto<ReadNormalPostResponseDto> responses = PageUtils.convert(new PageImpl<>(
                List.of(NormalPostUtils.convert(normalPost), NormalPostUtils.convert(normalPost))));

        given(normalPostReadService.readNormalPostsByUserNo(anyLong(), any(Pageable.class))).willReturn(responses);

        mockMvc.perform(get(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .param("page", "1L")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        mapper.writeValueAsString(responses)
                ))
                .andDo(print())
                .andDo(document("read-normal-post-by-userNo",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                headerWithName("content-type").description("보내는 데이터의 형식")
                        ),
                        responseFields(
                                fieldWithPath("content[].post.postNo").description("게시물 번호"),
                                fieldWithPath("content[].post.content").description("게시물의 내용"),
                                fieldWithPath("content[].post.createAt").description("최초 작성 시간"),
                                fieldWithPath("content[].post.modifyAt").description("마지막 수정 시간"),
                                fieldWithPath("content[].userNo").description("게시글을 작성한 유저 번호"),
                                fieldWithPath("pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("totalPages").description("전체 페이지 갯수"),
                                fieldWithPath("totalElements").description("전체 요소 갯수")
                        )
                ));
    }
}