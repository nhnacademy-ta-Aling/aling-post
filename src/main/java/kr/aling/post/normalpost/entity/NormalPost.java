package kr.aling.post.normalpost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * NormalPost(일반 게시글) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "normal_post")
public class NormalPost {
    @Id
    @Column(name = "post_no")
    private Long postNo;

    @MapsId("postNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no",insertable = false, updatable = false)
    private Post post;

    @Column(name = "normal_user_no")
    private Long userNo;

    /**
     * 일반 게시물 작성시, NormalPost 테이블 insert 에 필요한 컬럼의 값을 받는 생성자
     *
     * @param postNo 게시물 번호
     * @param userNo 작성자 번호
     *
     */
    @Builder
    public NormalPost(Long postNo, Long userNo) {
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
