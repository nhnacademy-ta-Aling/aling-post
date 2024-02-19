package kr.aling.post.postfile.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import kr.aling.post.post.dummy.PostDummy;
import kr.aling.post.post.entity.Post;
import kr.aling.post.postfile.dummy.PostFileDummy;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.postfile.repository.PostFileManageRepository;
import kr.aling.post.postfile.service.impl.PostFileManageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PostFileManageServiceTest {

    @InjectMocks
    PostFileManageServiceImpl postFileManageService;

    @Mock
    PostFileManageRepository postFileManageRepository;

    @Test
    @DisplayName("postFile 저장 서비스 테스트")
    void postFile_save_service_test() {
        // given
        List<Long> fileNoList = List.of(1L, 2L);
        Post post = PostDummy.postDummy();

        // when

        // then
        postFileManageService.savePostFiles(post, fileNoList);

        verify(postFileManageRepository, times(2)).save(any(PostFile.class));
    }


}