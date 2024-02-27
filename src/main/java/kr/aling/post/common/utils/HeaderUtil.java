package kr.aling.post.common.utils;


import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Some description here.
 *
 * @author 박경서
 * @since 1.0
 **/
public class HeaderUtil {

    private HeaderUtil() {
    }

    public static HttpHeaders makeHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }
}
