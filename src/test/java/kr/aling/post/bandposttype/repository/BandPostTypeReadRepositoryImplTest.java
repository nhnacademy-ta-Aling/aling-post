package kr.aling.post.bandposttype.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.dummy.BandPostTypeDummy;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepository;
import kr.aling.post.config.JpaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * 그룹 게시글 분류 조회 repository 테스트.
 *
 * @author 정유진
 * @since 1.0
 **/
@DataJpaTest
@Import(JpaConfig.class)
class BandPostTypeReadRepositoryImplTest {
    @Autowired
    BandPostTypeReadRepository bandPostTypeReadRepository;

    @Autowired
    TestEntityManager testEntityManager;

    BandPostType bandPostType;

    @BeforeEach
    public void setUp() {
        bandPostType = BandPostTypeDummy.bandPostTypeDummy();

        testEntityManager.persist(bandPostType);
    }

    @Test
    @DisplayName("그룹 게시글 분류명과 그룹명으로 그룹 게시글 존재 여부 확인_존재할 경우")
    void existsByNameAndBandNoTest_returnTrue() {
        boolean result = bandPostTypeReadRepository.existsByNameAndBandNo(bandPostType.getName(), bandPostType.getBandNo());
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("그룹 게시글 분류명과 그룹명으로 그룹 게시글 존재 여부 확인_존재 하지 않는 경우")
    void existsByNameAndBandNoTest_returnFalse() {
        boolean result = bandPostTypeReadRepository.existsByNameAndBandNo("존재 하지 않는 그룹 명 입니다", -100L);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("그룹 명으로 그룹 게시글 리스트 조회")
    void getBandPostTypeListByBandNo() {
        List<GetBandPostTypeResponseDto> resultList =
                bandPostTypeReadRepository.getBandPostTypeListByBandNo(bandPostType.getBandNo());

        assertThat(resultList).isNotEmpty();
        assertThat(resultList.get(0).getName()).isEqualTo(bandPostType.getName());
    }
}