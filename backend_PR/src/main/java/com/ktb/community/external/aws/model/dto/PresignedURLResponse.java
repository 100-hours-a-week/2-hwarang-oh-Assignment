package com.ktb.community.external.aws.model.dto;

import java.net.URL;

import lombok.Builder;

@Builder
public record PresignedURLResponse(
        String preSignedUrl) {
    public static PresignedURLResponse of(URL url) {
        return PresignedURLResponse.builder()
                .preSignedUrl(url.toString())
                .build();
    }
}
