package kr.aling.post.recruitbranchcode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RecruitBranchCode(채용 분야) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "recruit_branch_code")
public class RecruitBranchCode {
    @Id
    @Column(name = "recruit_branch_code_no")
    private String recruitBranchCodeNo;

    @Column(name = "recruit_branch_name")
    private String name;

}
