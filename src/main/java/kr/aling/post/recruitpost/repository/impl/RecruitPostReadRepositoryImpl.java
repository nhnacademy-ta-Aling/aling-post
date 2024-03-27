package kr.aling.post.recruitpost.repository.impl;

import kr.aling.post.recruitpost.entity.RecruitPost;
import kr.aling.post.recruitpost.repository.RecruitPostReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * 채용 공고 조회를 위한 Repository 구현체.
 *
 * @author 정유진
 * @since 1.0
 **/
public class RecruitPostReadRepositoryImpl extends QuerydslRepositorySupport implements
        RecruitPostReadRepositoryCustom {

    public RecruitPostReadRepositoryImpl() {
        super(RecruitPost.class);
    }


}
