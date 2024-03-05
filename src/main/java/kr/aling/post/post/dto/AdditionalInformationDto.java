package kr.aling.post.post.dto;

import lombok.Getter;

/**
 *
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class AdditionalInformationDto {
    private Boolean isBandPost;

    public AdditionalInformationDto(Boolean isBandPost) {
        this.isBandPost = isBandPost;
    }
    public void isBandPost(){
        this.isBandPost = true;
    }
}
