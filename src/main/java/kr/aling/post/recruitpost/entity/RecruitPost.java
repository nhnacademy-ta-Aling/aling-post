package kr.aling.post.recruitpost.entity;

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
import kr.aling.post.base.BaseCreateTimeEntity;
import kr.aling.post.locationcode.entity.LocationCode;
import kr.aling.post.recruitbranchcode.entity.RecruitBranchCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RecruitPost(채용 공고) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "recruit_post")
public class RecruitPost extends BaseCreateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_post_no")
    private Long recruitPostNo;

    @Column(name = "user_no")
    private Long userNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_code_no")
    private LocationCode locationCode;

    @ManyToOne
    @JoinColumn(name = "recruit_branch_code_no")
    private RecruitBranchCode recruitBranchCode;

    @Column(name = "recruit_post_title")
    private String title;

    @Column(name = "recruit_post_content")
    private String content;

    @Column(name = "recruit_post_salary")
    private Integer salary;

    @Column(name = "recruit_post_career_year")
    private String careerYear;

    @Column(name = "recruit_post_start_at")
    private LocalDateTime startAt;

    @Column(name = "recruit_post_end_at")
    private LocalDateTime endAt;

    @Column(name = "recruit_post_is_open")
    private Boolean isOpen;

    @Column(name = "recruit_post_is_delete")
    private Boolean isDelete;

    @PrePersist
    public void prePersist() {
        isOpen = Objects.isNull(isOpen) ? false : isOpen;
        isDelete = Objects.isNull(isDelete) ? false : isDelete;
    }
}
