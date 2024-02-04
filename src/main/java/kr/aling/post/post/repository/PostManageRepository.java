package kr.aling.post.post.repository;

import kr.aling.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostManageRepository extends JpaRepository<Post, Long> {

}
