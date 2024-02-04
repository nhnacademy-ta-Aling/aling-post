package kr.aling.post.post.repository;

import kr.aling.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReadRepository extends JpaRepository<Post, Long> {

}
