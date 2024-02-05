package kr.aling.post.normalpost.controller;

import java.net.URI;
import javax.validation.Valid;
import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
import kr.aling.post.normalpost.service.NormalPostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/normal-posts")
@RequiredArgsConstructor
public class NormalPostManageController {

    private final NormalPostManageService normalPostManageService;

    @PostMapping
    public ResponseEntity<Void> createNormalPost(@RequestBody @Valid CreateNormalPostRequest request,
                                                 @RequestParam long userNo) {

        normalPostManageService.createNormalPost(userNo, request);

        return ResponseEntity
                .created(URI.create("/normal-post"))
                .build();
    }

    @PutMapping("/{postNo}")
    public ResponseEntity<Void> modifyNormalPost(@RequestBody @Valid ModifyNormalPostRequest request,
                                                 @PathVariable long postNo,@RequestParam long userNo) {

        normalPostManageService.modifyNormalPost(postNo, request);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deleteNormalPost(@PathVariable long postNo){
        normalPostManageService.deleteById(postNo, 0L);

        return ResponseEntity
                .noContent()
                .build();
    }
}
