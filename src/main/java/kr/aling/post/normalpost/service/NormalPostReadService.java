package kr.aling.post.normalpost.service;

import java.util.List;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;
import kr.aling.post.normalpost.entity.NormalPost;
import kr.aling.post.post.exception.PostNotFoundException;

public interface NormalPostReadService {
    ReadNormalPostResponse readNormalPostByPostNo(Long postNo);

    NormalPost findById(Long postNo) throws PostNotFoundException;

    List<ReadNormalPostResponse> readNormalPostsByUserNo(long userNo);
}
