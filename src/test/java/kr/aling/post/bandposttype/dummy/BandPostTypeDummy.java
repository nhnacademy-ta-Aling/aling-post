package kr.aling.post.bandposttype.dummy;

import kr.aling.post.bandposttype.entity.BandPostType;

public class BandPostTypeDummy {

    public static BandPostType bandPostTypeDummy() {
        return BandPostType.builder()
                .bandNo(1L)
                .name("name")
                .build();
    }
}
