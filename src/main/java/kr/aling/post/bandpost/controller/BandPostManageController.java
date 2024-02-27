package kr.aling.post.bandpost.controller;

import static kr.aling.post.common.utils.ConstantUtil.X_BAND_USER_NO;

import javax.validation.Valid;
import kr.aling.post.bandpost.dto.request.CreateBandPostRequestDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
     * @return Status 201
     */
    @PostMapping
    public ResponseEntity<Void> createBandPost(@Valid @RequestBody CreateBandPostRequestDto createBandPostRequestDto,
            @RequestHeader(X_BAND_USER_NO) Long bandUserNo) {
        bandPostFacadeService.createBandPostFacade(createBandPostRequestDto, bandUserNo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
