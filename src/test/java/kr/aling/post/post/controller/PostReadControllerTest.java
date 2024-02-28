package kr.aling.post.post.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.service.PostReadService;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PostReadController.class)
class PostReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostReadService postReadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시물 존재 확인 성공")
    void isExistsPost() throws Exception {
        // given
        Long postNo = 1L;

        Mockito.when(postReadService.isExistsPost(anyLong())).thenReturn(new IsExistsPostResponseDto(Boolean.TRUE));

        // when
        ResultActions perform =
                mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/check-post/{postNo}", postNo));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isExists", equalTo(Boolean.TRUE)));

        // docs
        perform.andDo(document("post-is-exists-post",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("postNo").description("게시물 번호")),
                responseFields(
                        fieldWithPath("isExists").type(JsonFieldType.BOOLEAN).description("게시물 존재여부")
                )));
    }

    @Test
    @DisplayName("스크랩용 게시물 내용 조회 성공")
    void getPostsForScrap() throws Exception {
        // given
        Mockito.when(postReadService.getPostsForScrap(any())).thenReturn(new ReadPostsForScrapResponseDto(List.of(
                new ReadPostScrapsResponseDto(1L, "1", false, true),
                new ReadPostScrapsResponseDto(2L, "2", true, true),
                new ReadPostScrapsResponseDto(3L, "3", true, false)
        )));

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.addAll("postNos", List.of("1", "2", "3"));

        // when
        ResultActions perform =
                mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts-for-scrap")
                        .queryParams(request));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.infos[0].postNo", equalTo(1)))
                .andExpect(jsonPath("$.infos[0].content", equalTo("1")))
                .andExpect(jsonPath("$.infos[0].isDelete", equalTo(false)))
                .andExpect(jsonPath("$.infos[0].isOpen", equalTo(true)));

        // docs
        perform.andDo(document("post-read-for-scrap",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                        parameterWithName("postNos").description("게시물 번호 리스트")
                                .attributes(key(REQUIRED).value(REQUIRED_YES))
                                .attributes(key(VALID).value(""))
                ),
                responseFields(
                        fieldWithPath("infos").type(JsonFieldType.ARRAY).description("게시물 조회 리스트"),
                        fieldWithPath("infos[].postNo").type(JsonFieldType.NUMBER).description("게시물 번호"),
                        fieldWithPath("infos[].content").type(JsonFieldType.STRING).description("게시물 내용"),
                        fieldWithPath("infos[].isDelete").type(JsonFieldType.BOOLEAN).description("게시물 삭제여부"),
                        fieldWithPath("infos[].isOpen").type(JsonFieldType.BOOLEAN).description("게시물 공개여부")
                )));
    }
}