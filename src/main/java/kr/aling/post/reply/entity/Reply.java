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
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;

    @Column(name = "user_no")
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_parent_no")
    private Reply parentReply;

    @Column(name = "reply_content")
    private String content;

    @Column(name = "reply_modify_at")
    private LocalDateTime modifyAt;

    @Column(name = "reply_is_delete")
    private Boolean isDelete;

    @PrePersist
    public void prePersist() {
        isDelete = Objects.isNull(isDelete) ? false : isDelete;
    }
}
