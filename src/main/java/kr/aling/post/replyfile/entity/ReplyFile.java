package kr.aling.post.replyfile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.aling.post.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ReplyFile(댓글 파일) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "reply_file")
public class ReplyFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_file_no")
    private Long replyFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_no")
    private Reply reply;

    @Column(name = "file_no")
    private Long fileNo;
}
