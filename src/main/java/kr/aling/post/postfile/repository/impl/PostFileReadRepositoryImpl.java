package kr.aling.post.postfile.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import kr.aling.post.post.entity.QPost;
import kr.aling.post.postfile.dto.response.PostFileQueryDto;
import kr.aling.post.postfile.entity.PostFile;
import kr.aling.post.postfile.entity.QPostFile;
import kr.aling.post.postfile.repository.PostFileReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * PostFile Querydsl 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
public class PostFileReadRepositoryImpl extends QuerydslRepositorySupport implements PostFileReadRepositoryCustom {

    public PostFileReadRepositoryImpl() {
        super(PostFile.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param postNo 게시글 번호
     * @return 첨부 파일 번호 리스트
     */
    @Override
    public List<PostFileQueryDto> getPostFileByPostNo(Long postNo) {
        QPostFile postFile = QPostFile.postFile;
        QPost post = QPost.post;

        return from(post)
                .leftJoin(post.postFileList, postFile)
                .where(post.postNo.eq(postNo))
                .select(Projections.constructor(PostFileQueryDto.class,
                        postFile.fileNo))
                .fetch();
    }
}
