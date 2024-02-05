package kr.aling.post.normalpost.controller;

import java.util.List;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.exception.NormalPostNotFoundException;
import kr.aling.post.normalpost.service.NormalPostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/normal-posts")
@RequiredArgsConstructor
@RestController
public class NormalPostReadController {

    private final NormalPostReadService normalPostReadService;

    @GetMapping("/{postNo}")
    public ResponseEntity<ReadNormalPostResponse> readNormalPost(@PathVariable long postNo) {


        try {
            ReadNormalPostResponse response = normalPostReadService.readNormalPostByPostNo(postNo);

            return ResponseEntity.ok()
                    .body(response);
        } catch (NormalPostNotFoundException e) {
            return ResponseEntity.notFound()
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ReadNormalPostResponse>> readNormalPostsByUser(@RequestParam long userNo) {
        List<ReadNormalPostResponse> responses = normalPostReadService.readNormalPostsByUserNo(userNo);

        return ResponseEntity.ok()
                .body(responses);
    }
}
