package kr.aling.post.normalpost.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
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
 * 일반 게시물 작성, 수정, 삭제를 위한 컨트롤러입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/normal-posts", consumes = {"application/json"}, produces = {"application/json"})
@RequiredArgsConstructor
public class NormalPostManageController {

    private final NormalPostManageService normalPostManageService;

    /**
     * 일반 게시물 생성 요청
     *
     * @param request : 게시물 생성 요청 데이터
     * @param userNo  : 게시물을 작성하려는 유저 번호
     * @return : HTTP 상태 코드와 데이터를 가진 Response Entity
     */
    @PostMapping
    public ResponseEntity<Map<String, Long>> createNormalPost(@RequestBody @Valid CreateNormalPostRequest request,
                                                              @RequestParam long userNo) {
        Long postNo = normalPostManageService.createNormalPost(userNo, request);

        Map<String,Long> body = new HashMap<>();
        body.put("postNo", postNo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }

    /**
     * 일반 게시물 수정
     *
     * @param request : 게시물 수정 요청 정보
     * @param postNo  : 수정할 게시물의 번호
     * @return : HTTP 상태 코드를 가진 Response Entity
     */
    @PutMapping("/{postNo}")
    public ResponseEntity<Void> modifyNormalPost(@RequestBody @Valid ModifyNormalPostRequest request,
                                                 @PathVariable Long postNo) {

        normalPostManageService.modifyNormalPost(postNo, request);

        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * 일반 게시물 삭제
     *
     * @param postNo : 삭제할 게시물 번호
     * @return 상태 코드를 포함하는 Response Entity
     */
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deleteNormalPost(@PathVariable long postNo) {
        normalPostManageService.deleteById(postNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
