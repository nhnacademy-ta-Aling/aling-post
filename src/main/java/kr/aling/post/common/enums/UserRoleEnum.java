가package kr.aling.post.common.enums;

import lombok.Getter;

/**
 * Some description here.
 *
 * @author 정유진
 * @since 1.0
 **/
@Getter
public enum UserRoleEnum {
    ADMIN("ROLE_ADMIN"), COMPANY("ROLE_COMPANY"), USER("ROLE_USER");

    private String roleName;

    UserRoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
