package kr.aling.post.post.service;

import kr.aling.post.post.dto.request.CreatePostRequest;
import kr.aling.post.post.dto.request.ModifyPostRequest;
import kr.aling.post.post.dto.response.ReadPostResponse;

public interface PostManageService {
    ReadPostResponse createPost(CreatePostRequest request);

    ReadPostResponse modifyPost(Long postNo, ModifyPostRequest request);

    void privatePost(Long postNo);

    void deleteById(Long aLong);
}
