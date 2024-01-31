package kr.aling.post.postfile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.aling.post.post.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PostFile(게시글 파일) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "post_file")
public class PostFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_file_no")
    private Long postFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;

    @Column(name = "file_no")
    private Long fileNo;

}


