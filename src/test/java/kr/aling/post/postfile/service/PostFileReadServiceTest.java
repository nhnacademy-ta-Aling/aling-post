package kr.aling.post.postfile.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.repository.PostFileReadRepository;
import kr.aling.post.postfile.service.impl.PostFileReadServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PostFileReadServiceTest {

    @InjectMocks
    PostFileReadServiceImpl postFileReadService;

    @Mock
    PostFileReadRepository postFileReadRepository;

    @Test
    @DisplayName("게시글 파일 조회 서비스 테스트")
    void getPostFileNoList_service_test() {
        // given
        PostFileQueryDto postFileQueryDto = new PostFileQueryDto(1L);
        List<PostFileQueryDto> list = List.of(postFileQueryDto);

        // when
        when(postFileReadRepository.getPostFileByPostNo(anyLong())).thenReturn(list);

        // then
        postFileReadService.getPostFileNoList(anyLong());

        verify(postFileReadRepository, times(1)).getPostFileByPostNo(anyLong());
    }

}