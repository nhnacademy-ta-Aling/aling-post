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
import kr.aling.post.common.base.BaseCreateTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

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

    @LastModifiedDate
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
        isDelete = !(Objects.isNull(isDelete) || !isDelete);
    }

    /**
     * 최초에 글 작성시 사용하는 생성자, 내용과 공개 여부만 필요.
     *
     * @param content : 게시물 내용
     * @param isOpen  : 게시물 공개 여부
     * @author : 이성준
     * @since : 1.0
     */
    @Builder
    public Post(String content, Boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
    }

    /**
     * 게시물 엔티티 수정시 사용하는 setter 분류의 메서드입니다.
     *
     * @param replaceContent : 수정할 내용
     * @author : 이성준
     * @since : 1.0
     */
    public void modifyContent(String replaceContent) {
        this.content = replaceContent;
    }

    /**
     * 게시물 엔티티의 공개 여부를 전환할 때 사용하는 메서드입니다.
     *
     * @author : 이성준
     * @since : 1.0
     */
    public void switchVisibility() {
        this.isOpen = !isOpen;
    }

    /**
     * 게시물 삭제 요청시 DB 삭제가 아닌 soft delete 를 실행하는 메서드 입니다.
     *
     * @author : 이성준
     * @since : 1.0
     */
    public void softDelete() {
        this.isDelete = true;
    }

}
