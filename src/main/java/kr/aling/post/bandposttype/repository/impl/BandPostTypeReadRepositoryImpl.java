package kr.aling.post.bandposttype.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import kr.aling.post.bandposttype.dto.response.GetBandPostTypeResponseDto;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.bandposttype.entity.QBandPostType;
import kr.aling.post.bandposttype.repository.BandPostTypeReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 그룹 게시글 분류 조회를 위한 repository 구현체.
 *
 * @author : 정유진
 * @since : 1.0
 **/
public class BandPostTypeReadRepositoryImpl extends QuerydslRepositorySupport implements
        BandPostTypeReadRepositoryCustom {

    public BandPostTypeReadRepositoryImpl() {
        super(BandPostType.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name 그룹 게시글 분류 이름
     * @param bandNo 그룹 번호
     * @return 존재(중복) 여부
     */
    @Override
    public boolean existsByNameAndBandNo(String name, Long bandNo) {
        QBandPostType bandPostType = QBandPostType.bandPostType;

        Long count = from(bandPostType)
                .where(bandPostType.isDelete.isFalse()
                        .and(bandPostType.name.eq(name))
                        .and(bandPostType.bandNo.eq(bandNo)))
                .select(bandPostType.count())
                .fetchOne();

        return (count > 0);
    }

    @Override
    public List<GetBandPostTypeResponseDto> getBandPostTypeListByBandNo(Long bandNo) {
        QBandPostType bandPostType = QBandPostType.bandPostType;

        return from(bandPostType)
                .where(bandPostType.isDelete.isFalse()
                        .and(bandPostType.bandNo.eq(bandNo)))
                .select(Projections.constructor(GetBandPostTypeResponseDto.class,
                        bandPostType.name))
                .fetch();
    }
}
