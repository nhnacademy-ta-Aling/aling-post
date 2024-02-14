package kr.aling.post.post.service.impl;

import kr.aling.post.common.annotation.ManageService;
import kr.aling.post.post.dto.request.CreatePostRequestDto;
import kr.aling.post.post.dto.request.ModifyPostRequestDto;
import kr.aling.post.post.dto.response.CreatePostResponseDto;
import kr.aling.post.post.entity.Post;
import kr.aling.post.post.exception.PostNotFoundException;
import kr.aling.post.post.repository.PostManageRepository;
import kr.aling.post.post.repository.PostReadRepository;
import kr.aling.post.post.service.PostManageService;
import lombok.RequiredArgsConstructor;

/**
 * PostManageService 의 구현체입니다.
 * 엔티티의 수정이 발생하는 서비스 레이어 이기 때문에 스프링의 스테레오타입 Service 와 Transaction(readonly = false) 가 적용된 ManageService 커스텀 어노테이션이 적용되어 있습니다.
 *
 * @author : 이성준
 * @since : 1.0
 * @see kr.aling.post.common.annotation.ManageService
 */
@ManageService
@RequiredArgsConstructor
public class PostManageServiceImpl implements PostManageService {

    private final PostManageRepository postManageRepository;
    private final PostReadRepository postReadRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreatePostResponseDto createPost(CreatePostRequestDto request) {
        Post post = Post.builder()
                .content(request.getContent())
                .isOpen(request.getIsOpen())
                .build();

        return new CreatePostResponseDto(postManageRepository.save(post).getPostNo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyPost(Long postNo, ModifyPostRequestDto request) {
        Post post = this.findById(postNo);

        post.modifyContent(request.getContent());

        if (!post.isOpen().equals(request.getIsOpen())) {
            if (Boolean.TRUE.equals(request.getIsOpen())) {
                post.makePublic();
            } else {
                post.makePrivate();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void safeDeleteById(Long postNo) {
        this.findById(postNo).safeDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void privatePost(Long postNo) {
        this.findById(postNo).makePrivate();
    }

    /**
     * 게시물의 번호를 조건으로 엔티티를 조회하는 private 메서드입니다.
     * 엔티티 수정 목적으로 조회가 필요할 때 사용합니다.
     *
     * @param postNo 조회하려는 게시물의 번호
     * @return 번호에 해당하는 게시물이 존재하면 해당하는 게시물을 반환합니다.
     * @author : 이성준
     * @since : 1.0
     */
    private Post findById(Long postNo) {
        return postReadRepository.findById(postNo).orElseThrow(() -> new PostNotFoundException(postNo));
    }
}
