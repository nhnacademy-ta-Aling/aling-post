package kr.aling.post.recruittechskill.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.aling.post.recruitpost.entity.RecruitPost;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RecruitTechSkill(채용 공고 기술 스택) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "recruit_tech_skill")
public class RecruitTechSkill {

    @EmbeddedId
    private Pk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_post_no")
    private RecruitPost recruitPost;

    /**
     * RecruitTechSkill(채용 공고 기술 스택) 복합 키 클래스.
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "recruit_post_no")
        private Long recruitPostNo;

        @Column(name = "tech_skill_no")
        private Integer techSkillNo;
    }
}
