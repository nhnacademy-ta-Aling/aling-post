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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
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
 * @author : 이성준
 * @since : 1.0
 */
@WebMvcTest(NormalPostManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class NormalPostManageControllerTest {

    @Autowired
    MockMvc mockMvc;

    String mappedUrl = "/api/v1/normal-posts";

    @Autowired
    ObjectMapper mapper;

    @MockBean
    NormalPostManageService normalPostManageService;

    @Test
    @DisplayName("일반 게시물 생성")
    void createNormalPost() throws Exception {
        Long userNo = 1L;
        Long postNo = 1L;

        CreateNormalPostRequest createNormalPostRequest = new CreateNormalPostRequest();

        ReflectionTestUtils.setField(createNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(createNormalPostRequest, "isOpen", true);

        given(normalPostManageService.createNormalPost(any(), any(CreateNormalPostRequest.class))).willReturn(postNo);

        mockMvc.perform(post(mappedUrl)
                        .param("userNo", String.valueOf(userNo))
                        .content(mapper.writeValueAsString(createNormalPostRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-normal-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                headerWithName("content-type").description("보내는 데이터의 형식")
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
    @DisplayName("일반 게시물 수정")
    void modifyNormalPost() throws Exception {
        Long postNo = 1L;

        ModifyNormalPostRequest modifyNormalPostRequest = new ModifyNormalPostRequest();

        ReflectionTestUtils.setField(modifyNormalPostRequest, "content", "테스트용 일반 게시물 내용");
        ReflectionTestUtils.setField(modifyNormalPostRequest, "isOpen", false);

        doNothing().when(normalPostManageService).modifyNormalPost(any(),any(ModifyNormalPostRequest.class));

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
                                headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                headerWithName("content-type").description("보내는 데이터의 형식")
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정할 게시물 내용"),
                                fieldWithPath("isOpen").description("수정할 게시물의 공개 여부")
                        )
                ));

        then(normalPostManageService).should(times(1)).modifyNormalPost(any(),any(ModifyNormalPostRequest.class));
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
                                headerWithName("accept").description("응답 받을 데이터 형식에 대한 요청"),
                                headerWithName("content-type").description("보내는 데이터의 형식")
                        )
                ));
    }
}