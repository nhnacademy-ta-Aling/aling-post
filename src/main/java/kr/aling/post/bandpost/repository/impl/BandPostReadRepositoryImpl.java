package kr.aling.post.bandpost.repository.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import kr.aling.post.bandpost.dto.response.BandPostExceptFileQueryDto;
import kr.aling.post.bandpost.dto.response.BandPostQueryDto;
import kr.aling.post.bandpost.entity.BandPost;
import kr.aling.post.bandpost.entity.QBandPost;
import kr.aling.post.bandpost.repository.BandPostReadRepositoryCustom;
import kr.aling.post.post.entity.QPost;
import kr.aling.post.postfile.entity.QPostFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * BandPostReadRepositoryCustom 구현체.
 *
 * @author 박경서
 * @since 1.0
 **/
public class BandPostReadRepositoryImpl extends QuerydslRepositorySupport implements BandPostReadRepositoryCustom {

    public BandPostReadRepositoryImpl() {
        super(BandPost.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param postNo 게시글 번호
     * @return 게시글 정보 Query Dto
     */
    @Override
    public BandPostQueryDto getBandPostByPostNo(Long postNo) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;
        QBandPost bandPost = QBandPost.bandPost;

        return from(post)
                .leftJoin(post.postFileList, postFile)
                .innerJoin(bandPost).on(bandPost.postNo.eq(post.postNo))
                .where(post.postNo.eq(postNo))
                .orderBy(postFile.fileNo.asc())
                .distinct()
                .transform(
                        GroupBy.groupBy(post.postNo).list(
                                Projections.constructor(BandPostQueryDto.class,
                                        post.postNo,
                                        bandPost.title,
                                        post.content,
                                        bandPost.bandUserNo,
                                        post.createAt,
                                        post.modifyAt,
                                        post.isDelete,
                                        post.deleteReason,
                                        post.isOpen,
                                        GroupBy.list(Projections.constructor(BandPostQueryDto.AlingFileInfo.class,
                                                postFile.fileNo
                                        ))
                                )
                        )
                ).get(0);
    }

    /**
     * {@inheritDoc}
     *
     * @param bandNo   그룹 번호
     * @param pageable 페이징
     * @return 페이징 된 게시글 정보 QueryDto
     */
    @Override
    public Page<BandPostExceptFileQueryDto> getBandPostByBand(Long bandNo, Pageable pageable) {
        QPost post = QPost.post;
        QBandPost bandPost = QBandPost.bandPost;

        List<BandPostExceptFileQueryDto> content = from(post)
                .innerJoin(bandPost).on(bandPost.postNo.eq(post.postNo))
                .where(bandPost.bandNo.eq(bandNo)
                        .and(post.isDelete.isFalse())
                        .and(post.isOpen.isTrue()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.createAt.desc())
                .select(Projections.constructor(BandPostExceptFileQueryDto.class,
                        post.postNo,
                        bandPost.title,
                        post.content,
                        bandPost.bandUserNo,
                        post.createAt,
                        post.modifyAt,
                        post.isDelete,
                        post.deleteReason,
                        post.isOpen))
                .fetch();

        JPQLQuery<Long> count = from(post)
                .innerJoin(bandPost).on(bandPost.postNo.eq(post.postNo))
                .where(bandPost.bandNo.eq(bandNo)
                        .and(post.isDelete.isFalse())
                        .and(post.isOpen.isTrue()))
                .select(post.count());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
