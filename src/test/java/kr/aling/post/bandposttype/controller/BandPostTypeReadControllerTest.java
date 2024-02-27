package kr.aling.post.bandposttype.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
import kr.aling.post.bandposttype.service.BandPostTypeReadService;
import org.junit.jupiter.api.BeforeEach;
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
 * 그룹 게시글 분류 조회 Controller 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@WebMvcTest(BandPostTypeReadController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostTypeReadControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BandPostTypeReadService bandPostTypeReadService;

    GetBandPostTypeResponseDto getResponseDto;

    private final String url = "/api/v1/bands/{bandNo}/band-post-types";

    @BeforeEach
    public void setUp() {
        getResponseDto = new GetBandPostTypeResponseDto("testType");
    }

    @Test
    @DisplayName("그룹 게시글 분류 리스트 조회")
    void getBandPostTypeList() throws Exception {
        // given
        Long bandNo = 1L;

        // when
        when(bandPostTypeReadService.getBandPostTypeList(anyLong())).thenReturn(List.of(getResponseDto));

        // then
        mvc.perform(RestDocumentationRequestBuilders.get(url, bandNo)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("band-post-type-get-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("bandNo").description("그룹 번호")
                        ),
                        responseFields(
                                fieldWithPath("[].name").description("그룹 게시글 타입명")
                        )));

        verify(bandPostTypeReadService, times(1)).getBandPostTypeList(anyLong());
    }

}