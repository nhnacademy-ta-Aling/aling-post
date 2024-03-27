package kr.aling.post.post.controller;

import static kr.aling.post.util.RestDocsUtil.REQUIRED;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_NO;
import static kr.aling.post.util.RestDocsUtil.REQUIRED_YES;
import static kr.aling.post.util.RestDocsUtil.VALID;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.PostAdditionalInformationDto;
import kr.aling.post.post.dto.response.ReadPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.service.PostReadService;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import kr.aling.post.reply.dto.response.ReadUserInfoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.util.ReflectionTestUtils;
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

    ReadPostResponseIntegrationDto readPostResponseIntegrationDto;
    ReadPostResponseDto readPostResponseDto;
    ReadUserInfoResponseDto readUserInfoResponseDto;
    GetFileInfoResponseDto getFileInfoResponseDto;
    PostAdditionalInformationDto postAdditionalInformationDto;

    @BeforeEach
    void setUp() {
        readPostResponseDto = ReadPostResponseDto.builder()
                .postNo(1L)
                .content("content")
                .createAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now().plusDays(2L))
                .build();

        readUserInfoResponseDto = new ReadUserInfoResponseDto(1L, "user 이름", "image path");
        getFileInfoResponseDto = new GetFileInfoResponseDto();
        ReflectionTestUtils.setField(getFileInfoResponseDto, "fileNo", 1L);
        ReflectionTestUtils.setField(getFileInfoResponseDto, "categoryNo", 1);
        ReflectionTestUtils.setField(getFileInfoResponseDto, "categoryName", "profile");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "path", "image path");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "originName", "사진.png");
        ReflectionTestUtils.setField(getFileInfoResponseDto, "fileSize", "300KB");

        postAdditionalInformationDto = PostAdditionalInformationDto.builder()
                .isOpen(true)
                .bandNo(1L)
                .title("title")
                .postTypeNo(1L)
                .postTypeName("default")
                .build();

    }

    @Test
    @DisplayName("게시물 존재 확인 성공")
    void isExistsPost() throws Exception {
        // given
        Long postNo = 1L;

        when(postReadService.isExistsPost(anyLong())).thenReturn(new IsExistsPostResponseDto(Boolean.TRUE));

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
        when(postReadService.getPostsForScrap(any())).thenReturn(new ReadPostsForScrapResponseDto(List.of(
                new ReadPostScrapsPostResponseDto(1L, "1", false, true),
                new ReadPostScrapsPostResponseDto(2L, "2", true, true),
                new ReadPostScrapsPostResponseDto(3L, "3", true, false)
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

    @Test
    @DisplayName("게시글 단건 조회 api 테스트")
    void getPost_byPostNo_api_test() throws Exception {
        // given
        readPostResponseIntegrationDto = ReadPostResponseIntegrationDto.builder()
                .post(readPostResponseDto)
                .writer(readUserInfoResponseDto)
                .files(List.of(getFileInfoResponseDto))
                .additional(postAdditionalInformationDto)
                .build();

        // when
        when(postReadService.readPostByPostNo(anyLong())).thenReturn(readPostResponseIntegrationDto);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{postNo}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-single-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        responseFields(
                                fieldWithPath("post.postNo").description("게시글 번호"),
                                fieldWithPath("post.content").description("게시글 내용"),
                                fieldWithPath("post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("post.modifyAt").description("게시글 수정 시간"),

                                fieldWithPath("writer.userNo").description("게시글 작성자 유저 번호"),
                                fieldWithPath("writer.username").description("게시글 작성자 유저 이름"),
                                fieldWithPath("writer.profilePath").description("게시글 작성자 유저 프로필 사진 경로"),

                                fieldWithPath("files[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("files[].categoryNo").description("게시글 첨부 파일 Category 번호"),
                                fieldWithPath("files[].categoryName").description("게시글 첨부 파일 Category 이름"),
                                fieldWithPath("files[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("files[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("files[].fileSize").description("게시글 첨부 파일 크기"),

                                fieldWithPath("additional.isOpen").description("게시글 공개 여부"),
                                fieldWithPath("additional.bandNo").description("그룹 게시글 회원 번호"),
                                fieldWithPath("additional.title").description("그룹 게시글 제목"),
                                fieldWithPath("additional.postTypeNo").description("그룹 게시글 Category 번호"),
                                fieldWithPath("additional.postTypeName").description("그룹 게시글 Category 이름")
                        )
                ));

        verify(postReadService, times(1)).readPostByPostNo(anyLong());
    }

    @Test
    @DisplayName("게시글 여러개 조회 api 테스트")
    void getPosts_paging_api_test() throws Exception {
        // given
        readPostResponseIntegrationDto = ReadPostResponseIntegrationDto.builder()
                .post(readPostResponseDto)
                .writer(readUserInfoResponseDto)
                .files(List.of(getFileInfoResponseDto))
                .additional(postAdditionalInformationDto)
                .build();

        PageResponseDto<ReadPostResponseIntegrationDto> page =
                new PageResponseDto<>(0, 1, 1L, List.of(readPostResponseIntegrationDto));
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(postReadService.readPostsThatIsOpen(pageable)).thenReturn(page);

        // then
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-paging-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestParameters(
                                parameterWithName("page").description("페이지 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_NO)),
                                parameterWithName("size").description("페이지 크기")
                                        .attributes(key(REQUIRED).value(REQUIRED_NO))
                        ),

                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 번호"),
                                fieldWithPath("totalPages").description("페이지 개수"),
                                fieldWithPath("totalElements").description("전체 요소 개수"),

                                fieldWithPath("content[].post.postNo").description("게시글 번호"),
                                fieldWithPath("content[].post.content").description("게시글 내용"),
                                fieldWithPath("content[].post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("content[].post.modifyAt").description("게시글 수정 시간"),

                                fieldWithPath("content[].writer.userNo").description("게시글 작성자 유저 번호"),
                                fieldWithPath("content[].writer.username").description("게시글 작성자 유저 이름"),
                                fieldWithPath("content[].writer.profilePath").description("게시글 작성자 유저 프로필 사진 경로"),

                                fieldWithPath("content[].files[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("content[].files[].categoryNo").description("게시글 첨부 파일 Category 번호"),
                                fieldWithPath("content[].files[].categoryName").description("게시글 첨부 파일 Category 이름"),
                                fieldWithPath("content[].files[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("content[].files[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("content[].files[].fileSize").description("게시글 첨부 파일 크기"),

                                fieldWithPath("content[].additional.isOpen").description("게시글 공개 여부"),
                                fieldWithPath("content[].additional.bandNo").description("그룹 게시글 회원 번호"),
                                fieldWithPath("content[].additional.title").description("그룹 게시글 제목"),
                                fieldWithPath("content[].additional.postTypeNo").description("그룹 게시글 Category 번호"),
                                fieldWithPath("content[].additional.postTypeName").description("그룹 게시글 Category 이름")
                        )
                ));

        verify(postReadService, times(1)).readPostsThatIsOpen(any());
    }

    @Test
    @DisplayName("회원별 일반 게시글 페이징 조회 api 테스트")
    void normalPosts_paging_byUserNo_api_test() throws Exception {
        // given
        PostAdditionalInformationDto additionalInformationDto = PostAdditionalInformationDto.builder()
                .isOpen(true)
                .build();

        readPostResponseIntegrationDto = ReadPostResponseIntegrationDto.builder()
                .post(readPostResponseDto)
                .writer(readUserInfoResponseDto)
                .files(List.of(getFileInfoResponseDto))
                .additional(additionalInformationDto)
                .build();

        PageResponseDto<ReadPostResponseIntegrationDto> page =
                new PageResponseDto<>(0, 1, 1L, List.of(readPostResponseIntegrationDto));
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(postReadService.getNormalPostsByUserNo(anyLong(), any())).thenReturn(page);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{userNo}/normal-posts", 1L)
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-normal-byUser-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestParameters(
                                parameterWithName("page").description("페이지 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES)),
                                parameterWithName("size").description("페이지 크기")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),

                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 번호"),
                                fieldWithPath("totalPages").description("페이지 개수"),
                                fieldWithPath("totalElements").description("전체 요소 개수"),

                                fieldWithPath("content[].post.postNo").description("게시글 번호"),
                                fieldWithPath("content[].post.content").description("게시글 내용"),
                                fieldWithPath("content[].post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("content[].post.modifyAt").description("게시글 수정 시간"),

                                fieldWithPath("content[].writer.userNo").description("게시글 작성자 유저 번호"),
                                fieldWithPath("content[].writer.username").description("게시글 작성자 유저 이름"),
                                fieldWithPath("content[].writer.profilePath").description("게시글 작성자 유저 프로필 사진 경로"),

                                fieldWithPath("content[].files[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("content[].files[].categoryNo").description("게시글 첨부 파일 Category 번호"),
                                fieldWithPath("content[].files[].categoryName").description("게시글 첨부 파일 Category 이름"),
                                fieldWithPath("content[].files[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("content[].files[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("content[].files[].fileSize").description("게시글 첨부 파일 크기"),

                                fieldWithPath("content[].additional.isOpen").description("게시글 공개 여부"),
                                fieldWithPath("content[].additional.bandNo").description("미사용"),
                                fieldWithPath("content[].additional.title").description("미사용"),
                                fieldWithPath("content[].additional.postTypeNo").description("미사용"),
                                fieldWithPath("content[].additional.postTypeName").description("미사용")
                        )
                ));

        verify(postReadService, times(1)).getNormalPostsByUserNo(anyLong(), any());
    }

    @Test
    @DisplayName("회원별 그룹 게시글 페이징 조회 api test")
    void bandPosts_paging_byUserNo_api_test() throws Exception {
        // given
        readPostResponseIntegrationDto = ReadPostResponseIntegrationDto.builder()
                .post(readPostResponseDto)
                .writer(readUserInfoResponseDto)
                .files(List.of(getFileInfoResponseDto))
                .additional(postAdditionalInformationDto)
                .build();

        PageResponseDto<ReadPostResponseIntegrationDto> page =
                new PageResponseDto<>(0, 1, 1L, List.of(readPostResponseIntegrationDto));
        Pageable pageable = PageRequest.of(0, 10);

        // when
        when(postReadService.getBandPostsByUserNo(anyLong(), any())).thenReturn(page);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{userNo}/band-posts", 1L)
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-band-byUser-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        requestParameters(
                                parameterWithName("page").description("페이지 번호")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES)),
                                parameterWithName("size").description("페이지 크기")
                                        .attributes(key(REQUIRED).value(REQUIRED_YES))
                        ),

                        responseFields(
                                fieldWithPath("pageNumber").description("페이지 번호"),
                                fieldWithPath("totalPages").description("페이지 개수"),
                                fieldWithPath("totalElements").description("전체 요소 개수"),

                                fieldWithPath("content[].post.postNo").description("게시글 번호"),
                                fieldWithPath("content[].post.content").description("게시글 내용"),
                                fieldWithPath("content[].post.createAt").description("게시글 작성 시간"),
                                fieldWithPath("content[].post.modifyAt").description("게시글 수정 시간"),

                                fieldWithPath("content[].writer.userNo").description("게시글 작성자 유저 번호"),
                                fieldWithPath("content[].writer.username").description("게시글 작성자 유저 이름"),
                                fieldWithPath("content[].writer.profilePath").description("게시글 작성자 유저 프로필 사진 경로"),

                                fieldWithPath("content[].files[].fileNo").description("게시글 첨부 파일 번호"),
                                fieldWithPath("content[].files[].categoryNo").description("게시글 첨부 파일 Category 번호"),
                                fieldWithPath("content[].files[].categoryName").description("게시글 첨부 파일 Category 이름"),
                                fieldWithPath("content[].files[].path").description("게시글 첨부 파일 경로"),
                                fieldWithPath("content[].files[].originName").description("게시글 첨부 파일 원본 이름"),
                                fieldWithPath("content[].files[].fileSize").description("게시글 첨부 파일 크기"),

                                fieldWithPath("content[].additional.isOpen").description("게시글 공개 여부"),
                                fieldWithPath("content[].additional.bandNo").description("미사용"),
                                fieldWithPath("content[].additional.title").description("미사용"),
                                fieldWithPath("content[].additional.postTypeNo").description("미사용"),
                                fieldWithPath("content[].additional.postTypeName").description("미사용")
                        )
                ));

        verify(postReadService, times(1)).getBandPostsByUserNo(anyLong(), any());
    }

}