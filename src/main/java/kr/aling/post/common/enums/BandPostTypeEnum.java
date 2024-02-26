package kr.aling.post.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 그룹 게시글 분류 Enum.
 *
 * @author 정유진
 * @since 1.0
 **/
@Getter
@RequiredArgsConstructor
public enum BandPostTypeEnum {
    DEFAULT("default");

    private final String bandPostTypeName;
}

