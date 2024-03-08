package kr.aling.post.bandposttype.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * BandPostType(그룹 게시글 분류) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "band_post_type")
public class BandPostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_post_type_no")
    private Long bandPostTypeNo;

    @Column(name = "band_no")
    private Long bandNo;

    @Column(name = "band_post_type_name")
    private String name;

    @Column(name = "band_post_type_is_delete")
    private Boolean isDelete;

    @PrePersist
    public void prePersist() {
        isDelete = !(Objects.isNull(isDelete) || !isDelete);
    }

    /**
     * 그룹 게시글 분류(BandPostType) 생성자.
     *
     * @param bandNo 그룹 번호
     * @param name   분류 이름
     */
    @Builder
    public BandPostType(Long bandNo, String name) {
        this.bandNo = bandNo;
        this.name = name;
    }

    public void updatePostType(String name) {
        this.name = name;
    }

    public void deletePostType() {
        this.isDelete = true;
    }
}
