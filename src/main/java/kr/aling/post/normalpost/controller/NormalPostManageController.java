package kr.aling.post.normalpost.controller;

import javax.validation.Valid;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequestDto;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequestDto;
import kr.aling.post.normalpost.dto.response.CreateNormalPostResponseDto;
import kr.aling.post.normalpost.service.NormalPostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반 게시물 작성, 수정, 삭제를 위한 컨트롤러입니다. accept 헤더와 content-type 헤더를 application/json 를 기본으로 요구합니다.
 *
 * @author : 이성준
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/normal-posts", consumes = {"application/json"}, produces = {"application/json"})
@RequiredArgsConstructor
public class NormalPostManageController {

    private final NormalPostManageService normalPostManageService;

    /**
     * 일반 게시물 생성 요청.
     *
     * @param request 게시물 생성 요청 데이터
     * @param userNo  게시물을 작성하려는 유저 번호
     * @return HTTP 상태 코드와 데이터를 가진 Response Entity
     * @author : 이성준
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<CreateNormalPostResponseDto> createNormalPost(
            @RequestBody @Valid CreateNormalPostRequestDto request,
            @RequestParam Long userNo) {
        CreateNormalPostResponseDto response = normalPostManageService.createNormalPost(userNo, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * 일반 게시물 수정.
     *
     * @param request 게시물 수정 요청 정보
     * @param postNo  수정할 게시물의 번호
     * @return HTTP 상태 코드를 가진 Response Entity
     * @author : 이성준
     * @since 1.0
     */
    @PutMapping("/{postNo}")
    public ResponseEntity<Void> modifyNormalPost(@RequestBody @Valid ModifyNormalPostRequestDto request,
            @PathVariable Long postNo) {

        normalPostManageService.modifyNormalPost(postNo, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 일반 게시물 삭제.
     *
     * @param postNo 삭제할 게시물 번호
     * @return 상태 코드를 포함하는 Response Entity
     * @author : 이성준
     * @since 1.0
     */
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deleteNormalPost(@PathVariable Long postNo) {
        normalPostManageService.safeDeleteById(postNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
