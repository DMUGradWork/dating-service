package com.grewmeet.datingservice.util;

import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UriUtils {

    private UriUtils() {} // 인스턴스 생성 방지

    public static URI buildLocationUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
