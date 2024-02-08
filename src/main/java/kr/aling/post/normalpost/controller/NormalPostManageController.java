package kr.aling.post.normalpost.controller;

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
@RequestMapping("/api/v1/normal-posts")
@RequiredArgsConstructor
public class NormalPostManageController {

    private final NormalPostManageService normalPostManageService;

    /**
     * Create normal post response entity.
     *
     * @param request the request
     * @param userNo  the user no
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Void> createNormalPost(@RequestBody @Valid CreateNormalPostRequest request,
                                                 @RequestParam long userNo) {
        normalPostManageService.createNormalPost(userNo, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * Modify normal post response entity.
     *
     * @param request the request
     * @param postNo  the post no
     * @return the response entity
     */
    @PutMapping("/{postNo}")
    public ResponseEntity<Void> modifyNormalPost(@RequestBody @Valid ModifyNormalPostRequest request,
                                                 @PathVariable long postNo) {

        normalPostManageService.modifyNormalPost(postNo, request);

        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * Delete normal post response entity.
     *
     * @param postNo the post no
     * @return the response entity
     */
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deleteNormalPost(@PathVariable long postNo){
        normalPostManageService.deleteById(postNo);

        return ResponseEntity
                .noContent()
                .build();
    }
}
