package kr.aling.post.bandpost.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BandPost(그룹 게시글) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "band_post")
public class BandPost {

    @Id
    @Column(name = "post_no")
    private Long postNo;

    @MapsId("postNo")
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "post_no")
    private Post post;

    @Column(name = "band_no")
    private Long bandNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_post_type_no")
    private BandPostType bandPostType;

    @Column(name = "band_user_no")
    private Long bandUserNo;

    @Column(name = "band_post_title")
    private String title;

    /**
     * 그룹 게시글(BandPost) 생성자.
     *
     * @param postNo       게시글 번호
     * @param post         게시글
     * @param bandNo       그룹 번호
     * @param bandPostType 게시글 분류
     * @param bandUserNo   그룹 회원 번호
     * @param title        제목
     */
    @Builder
    public BandPost(Long postNo, Post post, Long bandNo, BandPostType bandPostType, Long bandUserNo, String title) {
        this.postNo = postNo;
        this.post = post;
        this.bandNo = bandNo;
        this.bandPostType = bandPostType;
        this.bandUserNo = bandUserNo;
        this.title = title;
    }
}
