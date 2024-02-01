package kr.aling.post.bandpost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.aling.post.bandposttype.entity.BandPostType;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_post_type_no")
    private BandPostType bandPostType;

    @Column(name = "band_user_no")
    private Long bandUserNo;

    @Column(name = "band_post_title")
    private String title;
}
