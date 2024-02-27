package kr.aling.post.bandposttype.controller;

import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeDefaultRequestDto;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 그룹 게시글 분류를 관리하기 위한 Rest Controller.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/band-post-types")
public class BandPostTypeManageController {

    private final BandPostTypeManageService bandPostTypeManageService;

    /**
     * 기본 그룹 게시물 분류를 생성 하기 위한 메서드입니다. <br> 그룹 생성 시 사용됩니다.
     *
     * @param requestDto 기본 그룹 게시물 분류를 생성할 그룹 정보 dto
     * @return 201 created
     */
    @PostMapping("/default")
    public ResponseEntity<Void> makeDefaultBandPostType(@RequestBody CreateBandPostTypeDefaultRequestDto requestDto) {
        bandPostTypeManageService.makeDefaultType(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 게시물 분류를 생성 하기 위한 메서드입니다.
     *
     * @param requestDto 그룹 게시물 분류를 생성할 그룹 정보와 분류 이름 dto
     * @return 201 created
     */
    @PostMapping
    public ResponseEntity<Void> makeBandPostType(@RequestBody CreateBandPostTypeRequestDto requestDto) {
        bandPostTypeManageService.makeBandPostType(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
