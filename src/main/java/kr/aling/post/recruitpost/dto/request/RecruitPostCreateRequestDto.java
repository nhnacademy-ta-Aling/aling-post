package kr.aling.post.recruitpost.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 채용 공고를 만들기 위해 필요한 정보를 담는 dto.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor
@Getter
public class RecruitPostCreateRequestDto {
    @NotBlank
    @Size(max = 20)
    private String locationCodeNo;

    @NotBlank
    @Size(max = 5)
    private String recruitBranchCodeNo;

    @NotBlank
    @Size(max = 10)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String content;

    private Integer salary;

    @NotBlank
    @Size(max = 8)
    private String careerYear;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endAt;

    @NotNull
    private Boolean isOpen;
}
