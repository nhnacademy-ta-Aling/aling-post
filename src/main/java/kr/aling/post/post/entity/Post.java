package kr.aling.post.post.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kr.aling.post.base.BaseCreateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Post(게시글) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post")
public class Post extends BaseCreateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private Long postNo;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_modify_at")
    private LocalDateTime modifyAt;

    @Column(name = "post_is_delete")
    private Boolean isDelete;

    @Column(name = "post_delete_reason")
    private String deleteReason;

    @Column(name = "post_is_open")
    private Boolean isOpen;

    @PrePersist
    public void prePersist() {
        isDelete = Objects.isNull(isDelete) ? false : isDelete;
    }
}
