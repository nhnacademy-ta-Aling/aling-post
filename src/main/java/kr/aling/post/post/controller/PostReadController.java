package kr.aling.post.post.controller;


import java.util.List;
import kr.aling.post.common.dto.PageResponseDto;
import kr.aling.post.post.dto.response.IsExistsPostResponseDto;
import kr.aling.post.post.dto.response.ReadPostResponseIntegrationDto;
import kr.aling.post.post.dto.response.ReadPostsForScrapResponseDto;
import kr.aling.post.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 게시물 조회 RestController.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class PostReadController {

    private final PostReadService postReadService;

    /**
     * 존재하는 게시물인지 확인합니다.
     *
     * @param postNo 확인할 게시물 번호
     * @return 게시물 존재 여부
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/check-post/{postNo}")
    public ResponseEntity<IsExistsPostResponseDto> isExistsPost(@PathVariable Long postNo) {
        return ResponseEntity.ok(postReadService.isExistsPost(postNo));
    }

    /**
     * 게시물 번호 리스트를 받아 스크랩 조회에 필요한 게시물 내용을 조회합니다.
     *
     * @param postNos 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용
     * @author 이수정
     * @since 1.0
     */
    @GetMapping("/posts-for-scrap")
    public ResponseEntity<ReadPostsForScrapResponseDto> getPostsForScrap(@RequestParam List<Long> postNos) {
        return ResponseEntity.ok().body(postReadService.getPostsForScrap(postNos));
    }

    @GetMapping("/posts/{postNo}")
    public ResponseEntity<ReadPostResponseIntegrationDto> getPostByPostNo(@PathVariable Long postNo) {
        return ResponseEntity.ok()
                .body(postReadService.readPostByPostNo(postNo));
    }

    @GetMapping("/posts")
    public ResponseEntity<PageResponseDto<ReadPostResponseIntegrationDto>> getPosts(Pageable pageable) {
        return ResponseEntity.ok()
                .body(postReadService.readPostsThatIsOpen(pageable));
    }

}
