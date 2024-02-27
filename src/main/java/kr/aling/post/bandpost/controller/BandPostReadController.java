package kr.aling.post.bandpost.controller;

import kr.aling.post.bandpost.dto.response.GetBandResponseDto;
import kr.aling.post.bandpost.service.facade.BandPostFacadeReadService;
import kr.aling.post.common.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BandPostReadController {

    private final BandPostFacadeReadService bandPostFacadeReadService;

    @GetMapping("/band-posts/{postNo}")
    public ResponseEntity<GetBandResponseDto> getBandPost(@PathVariable("postNo") Long postNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bandPostFacadeReadService.getBandPost(postNo));
    }

    @GetMapping("/bands/{bandNo}/posts")
    public ResponseEntity<PageResponseDto<GetBandResponseDto>> getBandPostsByBand(@PathVariable("bandNo") Long bandNo,
                                                                                  Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bandPostFacadeReadService.getBandPostByBand(bandNo, pageable));
    }

}
