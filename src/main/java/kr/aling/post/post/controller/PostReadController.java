package kr.aling.post.post.controller;


import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 게시물 조회 RestController.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostReadController {

    private final PostReadService postReadService;

    @GetMapping("/{postNo}")
    public ResponseEntity<IsExistsPostResponseDto> isExistsPost(@PathVariable Long postNo) {
        return ResponseEntity.ok(postReadService.isExistsPost(postNo));
    }
}
