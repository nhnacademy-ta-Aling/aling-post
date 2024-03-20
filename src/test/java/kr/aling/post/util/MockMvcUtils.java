package kr.aling.post.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * MockHttpServletRequestBuilder 의 중복된 코드를 추출한 유틸 클래스입니다.
 *
 * @author : 이성준
 * @since : 1.0
 */
public class MockMvcUtils {


    public static final String X_USER_NO = "X-User-No";

    public static MockHttpServletRequestBuilder buildRequest(MockHttpServletRequestBuilder builder) {

        return builder
                .header(X_USER_NO, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder buildRequest(MockHttpServletRequestBuilder builder, Object content)
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        return builder
                .header(X_USER_NO, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(content));
    }
}
