package com.ktb.community.global.service;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3Service {
    private final String BUCKET_NAME = "badamandarin";
    private final Region AWS_REGION = Region.AP_NORTHEAST_2;
    private final S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(AWS_REGION)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
