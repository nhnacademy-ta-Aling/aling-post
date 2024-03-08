package kr.aling.post.bandposttype.controller;

import javax.validation.Valid;
import kr.aling.post.bandposttype.dto.request.CreateBandPostTypeRequestDto;
import kr.aling.post.bandposttype.dto.request.ModifyBandPostTypeRequestDto;
import kr.aling.post.bandposttype.service.BandPostTypeManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 그룹 게시글 분류를 관리하기 위한 Rest Controller. <br>
 * 모든 그룹 게시글 분류에 관한 요청은 User 서버의 Band 를 거쳐 와야 합니다.
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
     * 그룹 게시물 분류를 생성 하기 위한 메서드입니다.
     *
     * @param requestDto 그룹 게시물 분류를 생성할 그룹 정보와 분류 이름 dto
     * @return 201 created
     */
    @PostMapping
    public ResponseEntity<Void> makeBandPostType(@Valid @RequestBody CreateBandPostTypeRequestDto requestDto) {
        bandPostTypeManageService.makeBandPostType(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 그룹 게시글 분류를 수정 하기 위한 메서드 입니다.
     *
     * @param postTypeNo 수정할 그룹 게시글 분류 번호
     * @param requestDto 수정할 그룹 게시글 분류 내용 dto
     * @return 200 ok
     */
    @PutMapping("/{postTypeNo}")
    public ResponseEntity<Void> updateBandPostType(
            @PathVariable("postTypeNo") Long postTypeNo,
            @Valid @RequestBody ModifyBandPostTypeRequestDto requestDto) {
        bandPostTypeManageService.updateBandPostType(postTypeNo, requestDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 그룹 게시글 분류를 삭제 하기 위한 메서드 입니다.<br>
     * 삭제되지 않은 그룹 게시글이 존재할 경우 삭제가 불가능 합니다.
     *
     * @param postTypeNo 삭제할 그룹 게시글 분류 번호
     * @return 204 no content
     */
    @DeleteMapping("/{postTypeNo}")
    public ResponseEntity<Void> deleteBandPostType(
            @PathVariable("postTypeNo") Long postTypeNo) {
        bandPostTypeManageService.deleteBandPostType(postTypeNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
