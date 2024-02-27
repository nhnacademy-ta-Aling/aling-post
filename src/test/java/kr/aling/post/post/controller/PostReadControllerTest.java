package kr.aling.post.post.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.service.PostReadService;
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

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PostReadController.class)
class PostReadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostReadService postReadService;

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

}