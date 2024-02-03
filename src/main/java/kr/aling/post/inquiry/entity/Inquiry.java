package kr.aling.post.inquiry.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.aling.post.common.base.BaseCreateTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Inquiry(문의) Entity.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inquiry")
@Entity
public class Inquiry extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_no")
    public Long inquiryNo;

    @Column(name = "aling_user_no")
    public Long userNo;

    @Column(name = "answer_aling_user_no")
    public Long answerUserNo;

    @Column(name = "inquiry_type")
    public String type;

    @Column(name = "inquiry_title")
    public String title;

    @Column(name = "inquiry_content")
    public String content;

    @Column(name = "inquiry_answer")
    public String answer;

    @Column(name = "inquiry_answer_at")
    public LocalDateTime answerAt;
}
