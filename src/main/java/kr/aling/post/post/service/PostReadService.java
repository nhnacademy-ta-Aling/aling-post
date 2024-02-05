package kr.aling.post.post.service;

import kr.aling.post.post.dto.response.ReadPostResponse;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;

public interface PostReadService {
    ReadPostResponse readPostByPostNo(Long postNo);

    Post findById(Long postNo) throws PostNotFoundException;
}
