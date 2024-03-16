package kr.aling.post.bandposttype.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 그룹 게시글 분류 조회 Controller 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@WebMvcTest(BandPostTypeReadController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostTypeReadControllerTest {

    private final String url = "/api/v1/band-post-types";
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BandPostTypeReadService bandPostTypeReadService;
    GetBandPostTypeResponseDto getResponseDto;

    @BeforeEach
    public void setUp() {
        getResponseDto = new GetBandPostTypeResponseDto(1L, "testType");
    }

    @Test
    @DisplayName("그룹 게시글 분류 리스트 조회")
    void getBandPostTypeList() throws Exception {
        // given
        Long bandNo = 1L;

        // when
        when(bandPostTypeReadService.getBandPostTypeList(anyLong())).thenReturn(List.of(getResponseDto));

        // then
        mvc.perform(RestDocumentationRequestBuilders.get(UriComponentsBuilder.fromUriString(url)
                                .queryParam("bandNo", bandNo)
                                .toUriString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(document("bandposttype-get-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("bandNo").description("그룹 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),
                        responseFields(
                                fieldWithPath("[].bandPostTypeNo").description("그룹 게시글 분류 번호"),
                                fieldWithPath("[].name").description("그룹 게시글 분류 명")
                        )));

        verify(bandPostTypeReadService, times(1)).getBandPostTypeList(anyLong());
    }

}