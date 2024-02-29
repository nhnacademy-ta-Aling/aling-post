package kr.aling.post.bandposttype.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
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
 * 그룹 게시글 분류 관리 Controller 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@WebMvcTest(BandPostTypeManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostTypeManageControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BandPostTypeManageService bandPostTypeManageService;

    CreateBandPostTypeRequestDto createRequestDto;
    private final String url = "/api/v1/band-post-types";

    @BeforeEach
    public void setUp() {
        createRequestDto = new CreateBandPostTypeRequestDto();
    }

    @Test
    @DisplayName("그룹 게시글 분류 생성 성공")
    void makeBandPostTypeSuccess() throws Exception {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "typeName");

        // when
        doNothing().when(bandPostTypeManageService).makeBandPostType(any(CreateBandPostTypeRequestDto.class));

        // then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isCreated())
                .andDo(document("band-post-type-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("bandNo").description("그룹 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Null")),
                                fieldWithPath("bandPostTypeName").description("생성할 그룹 게시글 분류 명")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                                        .attributes(key(VALID).value("Not Blank, 최대 10자"))
                        )
                ));

        verify(bandPostTypeManageService, times(1)).makeBandPostType(any(CreateBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 생성 실패_그룹 번호가 null")
    void makeBandPostTypeFail_bandNoIsNull() throws Exception {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", null);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "testTypeName");

        // when
        doNothing().when(bandPostTypeManageService).makeBandPostType(any(CreateBandPostTypeRequestDto.class));

        // then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).makeBandPostType(any(CreateBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 생성 실패_그룹 게시글 분류 명이 blank")
    void makeBandPostTypeFail_bandNoIsBlank() throws Exception {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "   ");

        // when
        doNothing().when(bandPostTypeManageService).makeBandPostType(any(CreateBandPostTypeRequestDto.class));

        // then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).makeBandPostType(any(CreateBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 생성 실패_그룹 게시글 분류 명 사이즈 초과")
    void makeBandPostTypeFail_bandNoIsOverSize() throws Exception {
        // given
        ReflectionTestUtils.setField(createRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(createRequestDto, "bandPostTypeName", "i".repeat(11));

        // when
        doNothing().when(bandPostTypeManageService).makeBandPostType(any(CreateBandPostTypeRequestDto.class));

        // then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).makeBandPostType(any(CreateBandPostTypeRequestDto.class));
    }

}