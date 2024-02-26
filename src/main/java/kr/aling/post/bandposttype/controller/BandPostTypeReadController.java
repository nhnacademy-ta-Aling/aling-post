package kr.aling.post.bandposttype.controller;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.service.BandPostTypeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 그룹 게시물 분류를 조회하기 위한 Rest Controller.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bands/{bandNo}/band-post-types")
public class BandPostTypeReadController {
    private final BandPostTypeReadService bandPostTypeReadService;

    /**
     * 그룹 게시물 분류 리스트를 조회 하기 위한 메서드입니다. <br>
     * 특정 그룹의 그룹 게시물 분류 리스트를 조회 합니다.
     *
     * @param bandNo 그룹 번호
     * @return 200 ok. 그룹 게시물 분류 정보 dto 리스트
     */
    @GetMapping
    public ResponseEntity<List<GetBandPostTypeResponseDto>> bandPostTypeList(@PathVariable("bandNo") Long bandNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bandPostTypeReadService.getBandPostTypeList(bandNo));
    }
}
