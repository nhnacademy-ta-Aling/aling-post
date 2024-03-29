package kr.aling.post.bandpost.service.facade;

import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.dto.request.ModifyBandPostRequestDto;
import kr.aling.post.bandpost.service.BandPostManageService;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.service.PostManageService;
import kr.aling.post.postfile.service.PostFileManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 그룹 게시글 파사드 Service.
 *
 * @author 박경서
 * @since 1.0
 **/
@Service
@Transactional
@RequiredArgsConstructor
public class BandPostFacadeService {

    private final PostManageService postManageService;
    private final PostFileManageService postFileManageService;
    private final BandPostManageService bandPostManageService;

    /**
     * 그룹 게시글 생성 하는 파사드 메서드. 다음 동작을 수행 합니다.
     * <ol>
     *     <li>게시글(post) 저장</li>
     *     <li>게시글 파일(post_file) 저장</li>
     *     <li>그룹 게시글(band_post) 저장</li>
     * </ol>
     *
     * @param createBandPostRequestDto 그룹 게시글 생성 요청 Dto
     * @param bandUserNo               그룹 회원 번호
     * @param alingUserNo              회원 번호
     */
    public void createBandPostFacade(CreateBandPostRequestDto createBandPostRequestDto, Long bandUserNo,
            Long alingUserNo) {
        CreatePostResponseDto createPostResponseDto = postManageService.createPost(createBandPostRequestDto);

        postFileManageService.savePostFiles(createPostResponseDto.getPost(), createBandPostRequestDto.getFileNoList());

        bandPostManageService.createBandPost(createPostResponseDto, createBandPostRequestDto, bandUserNo, alingUserNo);
    }

    /**
     * 그룹 게시글 수정 하는 파사드 메서드. 다음 동작을 수행 합니다.
     * <ol>
     *     <li>게시글(post) 내용, 수정 시간 수정</li>
     *     <li>그룹 게시글(band_post) 제목, Category 수정</li>
     * </ol>
     *
     * @param postNo                   게시글 번호
     * @param modifyBandPostRequestDto 그룹 게시글 수정 요청 Dto
     */
    public void modifyBandPostFacade(Long postNo, ModifyBandPostRequestDto modifyBandPostRequestDto) {
        postManageService.modifyBandPost(postNo, modifyBandPostRequestDto);
        bandPostManageService.modifyBandPost(postNo, modifyBandPostRequestDto);
    }

    /**
     * 그룹 게시글 삭제 메서드.
     *
     * @param postNo 게시글 번호
     */
    public void deleteBandPostFacade(Long postNo) {
        postManageService.safeDeleteById(postNo);
    }
}
