package kr.aling.post.bandpost.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import kr.aling.post.bandpost.dto.response.external.GetFileInfoResponseDto;
import lombok.Getter;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
@Getter
public class BandPostDto {

    private final Long postNo;
    private final String title;
    private final String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime modifyAt;
    private final Boolean isOpen;

    private final List<GetFileInfoResponseDto> file;

    public BandPostDto(BandPostQueryDto bandPostQueryDto, List<GetFileInfoResponseDto> fileInfoResponseDtoList) {
        this.postNo = bandPostQueryDto.getPostNo();
        this.title = bandPostQueryDto.getTitle();
        this.content = bandPostQueryDto.getContent();
        this.createAt = bandPostQueryDto.getCreateAt();
        this.modifyAt = bandPostQueryDto.getModifyAt();
        this.isOpen = bandPostQueryDto.getIsOpen();
        this.file = fileInfoResponseDtoList;
    }

    public BandPostDto(BandPostExceptFileQueryDto bandPostExceptFileQueryDto,
                       List<GetFileInfoResponseDto> fileInfoResponseDtoList) {
        this.postNo = bandPostExceptFileQueryDto.getPostNo();
        this.title = bandPostExceptFileQueryDto.getTitle();
        this.content = bandPostExceptFileQueryDto.getContent();
        this.createAt = bandPostExceptFileQueryDto.getCreateAt();
        this.modifyAt = bandPostExceptFileQueryDto.getModifyAt();
        this.isOpen = bandPostExceptFileQueryDto.getIsOpen();
        this.file = fileInfoResponseDtoList;
    }
}
