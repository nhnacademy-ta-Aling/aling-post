package kr.aling.post.bandposttype.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.request.ModifyBandPostTypeRequestDto;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
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
 * 그룹 게시글 분류 관리 Controller 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@WebMvcTest(BandPostTypeManageController.class)
@AutoConfigureRestDocs(uriPort = 9030)
class BandPostTypeManageControllerTest {
    private final String url = "/api/v1/band-post-types";
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    BandPostTypeManageService bandPostTypeManageService;
    CreateBandPostTypeRequestDto createRequestDto;
    ModifyBandPostTypeRequestDto modifyRequestDto;

    @BeforeEach
    public void setUp() {
        createRequestDto = new CreateBandPostTypeRequestDto();
        modifyRequestDto = new ModifyBandPostTypeRequestDto();
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
                .andDo(document("bandposttype-create",
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

    @Test
    @DisplayName("그룹 게시글 분류 수정 성공")
    void updateBandPostType_successTest() throws Exception {
        // given
        Long postTypeNo = 1L;

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "typeName");

        // when
        doNothing().when(bandPostTypeManageService)
                .updateBandPostType(anyLong(), any(ModifyBandPostTypeRequestDto.class));

        // then
        mvc.perform(put(url + "/{postTypeNo}", postTypeNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRequestDto)))
                .andExpect(status().isOk())
                .andDo(document("bandposttype-modify",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postTypeNo").description("수정할 그룹 게시글 분류 번호")
                        ),
                        requestFields(
                                fieldWithPath("bandNo").type(Number.class).description("그룹 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES),
                                                key(VALID).value("Not Null")),
                                fieldWithPath("bandPostTypeName").type(String.class).description("수정할 그룹 게시글 분류 명")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES),
                                                key(VALID).value("Not Blank, 최대 10글자"))
                        )
                ));

        verify(bandPostTypeManageService, times(1)).updateBandPostType(anyLong(),
                any(ModifyBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 수정 실패_그룹 번호가 null인 경우")
    void updateBandPostType_failTest_bandNoIsNull() throws Exception {
        // given
        Long postTypeNo = 1L;

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", null);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "typeName");

        // when
        doNothing().when(bandPostTypeManageService)
                .updateBandPostType(anyLong(), any(ModifyBandPostTypeRequestDto.class));

        // then
        mvc.perform(put(url + "/{postTypeNo}", postTypeNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).updateBandPostType(anyLong(),
                any(ModifyBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 수정 실패_그룹 게시글 분류 명이 blank 인 경우")
    void updateBandPostType_failTest_bandPostTypeNameIsBlank() throws Exception {
        // given
        Long postTypeNo = 1L;

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "  ");

        // when
        doNothing().when(bandPostTypeManageService)
                .updateBandPostType(anyLong(), any(ModifyBandPostTypeRequestDto.class));

        // then
        mvc.perform(put(url + "/{postTypeNo}", postTypeNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).updateBandPostType(anyLong(),
                any(ModifyBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 수정 실패_그룹 게시글 분류 명 사이즈 초과")
    void updateBandPostType_failTest_bandPostTypeName_sizeExceed() throws Exception {
        // given
        Long postTypeNo = 1L;

        ReflectionTestUtils.setField(modifyRequestDto, "bandNo", 1L);
        ReflectionTestUtils.setField(modifyRequestDto, "bandPostTypeName", "a".repeat(11));

        // when
        doNothing().when(bandPostTypeManageService)
                .updateBandPostType(anyLong(), any(ModifyBandPostTypeRequestDto.class));

        // then
        mvc.perform(put(url + "/{postTypeNo}", postTypeNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modifyRequestDto)))
                .andExpect(status().isBadRequest());

        verify(bandPostTypeManageService, times(0)).updateBandPostType(anyLong(),
                any(ModifyBandPostTypeRequestDto.class));
    }

    @Test
    @DisplayName("그룹 게시글 분류 삭제 성공")
    void deleteBandPostType_successTest() throws Exception {
        // given
        Long postTypeNo = 1L;

        // when
        doNothing().when(bandPostTypeManageService).deleteBandPostType(anyLong());

        // then
        mvc.perform(RestDocumentationRequestBuilders.delete(url + "/{postTypeNo}", postTypeNo))
                .andExpect(status().isNoContent())
                .andDo(document("bandposttype-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postTypeNo").description("삭제할 그룹 게시글 분류 번호")
                        )
                ));

        verify(bandPostTypeManageService, times(1)).deleteBandPostType(anyLong());
    }
}