package kr.aling.post.normalpost.service;


import kr.aling.post.normalpost.dto.request.CreateNormalPostRequest;
import kr.aling.post.normalpost.dto.request.ModifyNormalPostRequest;
import kr.aling.post.normalpost.dto.response.ReadNormalPostResponse;

public interface NormalPostManageService {
    void createNormalPost(long userNo, CreateNormalPostRequest request);

    ReadNormalPostResponse modifyNormalPost(Long postNo, ModifyNormalPostRequest request);

    void deleteById(Long postNo, Long userNo);
}
