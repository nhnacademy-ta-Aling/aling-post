package kr.aling.post.bandpost.controller;

import static kr.aling.post.common.utils.ConstantUtil.X_BAND_USER_NO;
import static kr.aling.post.common.utils.ConstantUtil.X_USER_NO;

import javax.validation.Valid;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.dto.request.ModifyBandPostRequestDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 그룹 게시글 생성, 수정, 삭제 API Controller.
 *
 * @author 박경서
 * @since 1.0
 **/
@RestController
@RequestMapping("/api/v1/band-posts")
@RequiredArgsConstructor
public class BandPostManageController {

    private final BandPostFacadeService bandPostFacadeService;

    /**
     * 그룹 게시글 생성 API.
     *
     * @param createBandPostRequestDto 게시글 생성 요청 dto
     * @param bandUserNo               그룹 회원 번호
     * @param alingUserNo              회원 번호
     * @return Status 201
     */
    @PostMapping
    public ResponseEntity<Void> createBandPost(@Valid @RequestBody CreateBandPostRequestDto createBandPostRequestDto,
            @RequestHeader(X_BAND_USER_NO) Long bandUserNo,
            @RequestHeader(X_USER_NO) Long alingUserNo) {
        bandPostFacadeService.createBandPostFacade(createBandPostRequestDto, bandUserNo, alingUserNo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 그룹 게시글 수정 API.
     *
     * @param postNo                   게시글 번호
     * @param modifyBandPostRequestDto 게시글 수정 요청 Dto
     * @return Status 200
     */
    @PutMapping("/{postNo}")
    public ResponseEntity<Void> modifyBandPost(@PathVariable("postNo") Long postNo,
            @Valid @RequestBody ModifyBandPostRequestDto modifyBandPostRequestDto) {
        bandPostFacadeService.modifyBandPostFacade(postNo, modifyBandPostRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 그룹 게시글 삭제 API.
     *
     * @param postNo 게시글 번호
     * @return Status 204
     */
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deletePost(@PathVariable("postNo") Long postNo,
                                           @RequestHeader("X-User-No") Long userNo) {
        bandPostFacadeService.deleteBandPostFacade(postNo, userNo);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
