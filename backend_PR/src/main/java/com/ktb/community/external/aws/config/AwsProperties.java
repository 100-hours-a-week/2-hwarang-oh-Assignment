package com.ktb.community.external.aws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsProperties(
        S3 s3,
        Credential credential,
        String region) {

    public record S3(String bucket, Long duration) {
    }

    public record Credential(String accessKey, String secretKey) {
    }
}
