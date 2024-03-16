package kr.aling.post.post.repository.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.stream.Collectors;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import kr.aling.post.bandpost.entity.QBandPost;
import kr.aling.post.bandposttype.entity.QBandPostType;
import kr.aling.post.normalpost.entity.QNormalPost;
import kr.aling.post.post.dto.response.BandPostResponseDto;
import kr.aling.post.post.dto.response.NormalPostResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.entity.QPost;
import kr.aling.post.post.repository.PostReadRepositoryCustom;
import kr.aling.post.postscrap.dto.response.ReadPostScrapsPostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

/**
 * QueryDsl 게시물 조회를 위한 repository.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@Repository
public class PostReadRepositoryImpl extends QuerydslRepositorySupport implements PostReadRepositoryCustom {

    public PostReadRepositoryImpl() {
        super(Post.class);
    }

    QPost post = QPost.post;
    QNormalPost normalPost = QNormalPost.normalPost;
    QBandPost bandPost = QBandPost.bandPost;
    QBandPostType bandPostType = QBandPostType.bandPostType;

    /**
     * {@inheritDoc}
     *
     * @param postNos 조회할 게시물 번호 리스트
     * @return 스크랩 조회에 필요한 게시물 내용 리스트
     */
    @Override
    public List<ReadPostScrapsPostResponseDto> getPostInfoForScrap(List<Long> postNos) {

        String temp = postNos.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        OrderSpecifier<?> orderSpecifier = Expressions.stringTemplate("FIELD({0}, {1})", post.postNo, temp).asc();

        String temp = postNos.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        OrderSpecifier<?> orderSpecifier = Expressions.stringTemplate("FIELD({0}, {1})", post.postNo, temp).asc();

        return from(post)
                .where(post.postNo.in(postNos))
                .orderBy(orderSpecifier)
                .select(Projections.constructor(ReadPostScrapsPostResponseDto.class,
                        post.postNo,
                        post.content.substring(0, 30),
                        post.isDelete,
                        post.isOpen))
                .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @param userNo   유저 번호
     * @param pageable 페이징
     * @return 일반 게시글 페이징 정보
     */
    @Override
    public Page<NormalPostResponseDto> getNormalPostsByUserNo(Long userNo, Pageable pageable) {

        List<NormalPostResponseDto> content = from(post)
                .innerJoin(post.normalPost, normalPost)
                .where(post.normalPost.userNo.eq(userNo)
                        .and(post.isDelete.isFalse()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.createAt.desc())
                .select(Projections.constructor(NormalPostResponseDto.class,
                        post.postNo,
                        post.content,
                        post.createAt,
                        post.modifyAt,
                        post.isOpen))
                .fetch();

        JPQLQuery<Long> count = from(post)
                .innerJoin(post.normalPost, normalPost)
                .where(normalPost.userNo.eq(userNo)
                        .and(post.isDelete.isFalse()))
                .select(post.count());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * {@inheritDoc}
     *
     * @param userNo   유저 번호
     * @param pageable 페이징
     * @return 그룹 게시글 페이징 정보
     */
    @Override
    public Page<BandPostResponseDto> getBandPostsByUserNo(Long userNo, Pageable pageable) {

        List<BandPostResponseDto> content = from(post)
                .innerJoin(post.bandPost, bandPost)
                .innerJoin(bandPost.bandPostType, bandPostType)
                .where(bandPost.userNo.eq(userNo)
                        .and(post.isDelete.isFalse()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(post.createAt.desc())
                .select(Projections.constructor(BandPostResponseDto.class,
                        bandPost.bandNo,
                        post.postNo,
                        bandPost.title,
                        post.content,
                        post.createAt,
                        post.modifyAt,
                        post.isOpen,
                        bandPostType.bandPostTypeNo,
                        bandPostType.name))
                .fetch();

        JPQLQuery<Long> count = from(post)
                .innerJoin(post.bandPost, bandPost)
                .innerJoin(bandPost.bandPostType, bandPostType)
                .where(bandPost.userNo.eq(userNo)
                        .and(post.isDelete.isFalse()))
                .select(post.count());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}
