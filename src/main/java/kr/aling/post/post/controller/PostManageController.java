package kr.aling.post.post.controller;

import kr.aling.post.post.service.PostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 이성준
 * @since : 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PostManageController {

    PostManageService postManageService;

    @DeleteMapping("/posts/{postNo}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postNo,
                                           @RequestHeader("X-User-No") Long userNo) {
        postManageService.softDeleteById(postNo, userNo);

        return ResponseEntity.noContent().build();
    }
}
