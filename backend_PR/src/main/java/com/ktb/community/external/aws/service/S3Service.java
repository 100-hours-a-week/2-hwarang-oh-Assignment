package com.ktb.community.external.aws.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.ktb.community.external.aws.config.AwsProperties;
import com.ktb.community.external.aws.model.dto.PresignedURLRequest;
import com.ktb.community.external.aws.model.dto.PresignedURLResponse;
import com.ktb.community.global.util.S3PathUtil;

import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class S3Service {

    private final AwsProperties awsProperties;
    private final S3Presigner s3Presigner;

    public S3Service(AwsProperties awsProperties, S3Presigner s3Presigner) {
        this.awsProperties = awsProperties;
        this.s3Presigner = s3Presigner;
    }

    public PresignedURLResponse generate(PresignedURLRequest presignedURLRequest) {
        String path = S3PathUtil.generatePath(presignedURLRequest.getEntity(), presignedURLRequest.getId(),
                presignedURLRequest.getFileName());

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(p -> p.bucket(awsProperties.s3().bucket()).key(path))
                .signatureDuration(Duration.ofSeconds(awsProperties.s3().duration()))
                .build();

        return PresignedURLResponse.of(s3Presigner.presignPutObject(presignRequest).url());
    }
}
