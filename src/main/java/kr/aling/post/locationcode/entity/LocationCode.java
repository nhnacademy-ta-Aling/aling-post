package kr.aling.post.locationcode.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LocationCode(지역 코드) Entity.
 *
 * @author 정유진
 * @since 1.0
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "location_code")
public class LocationCode {
    @Id
    @Column(name = "location_code_no")
    private String locationCodeNo;

    @Column(name = "location_name")
    private String name;
}
