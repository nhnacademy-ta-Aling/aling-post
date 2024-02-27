package kr.aling.post.reply.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kr.aling.post.common.base.BaseCreateTimeEntity;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * Reply(게시글 댓글) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "reply")
public class Reply extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_no")
    private Long replyNo;

    @Column(name = "post_no", nullable = false)
    private Long postNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", insertable = false, updatable = false)
    private Post post;

    @Column(name = "aling_user_no", nullable = false)
    private Long userNo;

    @Column(name = "reply_parent_no")
    private Long parentReplyNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_parent_no", insertable = false, updatable = false)
    private Reply parentReply;

    @Column(name = "reply_content", nullable = false)
    private String content;

    @LastModifiedDate
    @Column(name = "reply_modify_at")
    private LocalDateTime modifyAt;

    @Column(name = "reply_is_delete", nullable = false)
    private Boolean isDelete;

    /**
     * 댓글이 작성될 때, 필요한 초기 데이터 생성자입니다.
     *
     * @param postNo        댓글이 달릴 게시물의 번호.
     * @param userNo        댓글의 작성자 번호.
     * @param parentReplyNo 댓글에 추가로 달리는 대댓글인 경우 상위 댓글 번호.
     * @param content       댓글의 내용
     */
    @Builder
    public Reply(Long postNo, Long userNo, Long parentReplyNo, String content) {
        this.postNo = postNo;
        this.userNo = userNo;
        this.parentReplyNo = parentReplyNo;
        this.content = content;
    }

    @PrePersist
    public void prePersist() {
        isDelete = !Objects.isNull(isDelete) && isDelete;
    }

    /**
     * 댓글 내용 수정시 사용하는 메서드입니다.
     *
     * @param replaceContent 댓글 내용을 대체할 문자열
     * @author : 이성준
     * @since 1.0
     */
    public void modifyContent(String replaceContent) {
        this.content = replaceContent;
    }

    /**
     * 댓글 삭제시 데이터베이스 행 삭제가 아닌 삭제 처리합니다.
     *
     * @author : 이성준
     * @since 1.0
     */
    public void softDelete() {
        this.isDelete = true;
    }
}
