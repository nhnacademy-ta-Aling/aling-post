package kr.aling.post.locationcode.controller;

import java.util.List;
import kr.aling.post.locationcode.dto.response.LocationCodeGetResponseDto;
import kr.aling.post.locationcode.service.LocationCodeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 지역 코드 조회를 위한 Controller.
 *
 * @author 정유진
 * @since 1.0
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location-codes")
public class LocationCodeReadController {
    private final LocationCodeReadService locationCodeReadService;

    /**
     * 지역 코드 리스트 조회를 위한 메서드 입니다.
     *
     * @return 200 ok. 지역 코드 정보 dto 리스트.
     */
    @GetMapping
    public ResponseEntity<List<LocationCodeGetResponseDto>> locationCodeList() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationCodeReadService.getLocationCodeList());
    }
}
