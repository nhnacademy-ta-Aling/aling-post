package kr.aling.post.post.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.entity.QPost;
import kr.aling.post.post.repository.PostReadRepositoryCustom;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * QueryDsl 게시물 조회를 위한 repository.
 *
 * @author 이수정
 * @since 1.0
 */
@Repository
public class PostReadRepositoryImpl extends QuerydslRepositorySupport implements PostReadRepositoryCustom {

    public PostReadRepositoryImpl() {
        super(Post.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param postNos 조회할 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용 리스트
     */
    @Override
    public List<ReadPostScrapsPostResponseDto> getPostInfoForScrap(List<Long> postNos) {
        QPost post = QPost.post;

        return from(post)
                .where(post.postNo.in(postNos))
                .orderBy(post.createAt.desc())
                .select(Projections.constructor(ReadPostScrapsPostResponseDto.class,
                        post.postNo,
                        post.content.substring(0, 30),
                        post.isDelete,
                        post.isOpen))
                .fetch();
    }
}
