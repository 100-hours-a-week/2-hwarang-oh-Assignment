package com.ktb.community.external.aws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktb.community.external.aws.model.dto.PresignedURLRequest;
import com.ktb.community.external.aws.model.dto.PresignedURLResponse;
import com.ktb.community.external.aws.service.S3Service;

/**
 * IMP : S3 Controller ( REST API )
 * TYPE : EndPoint : /api/s3
 */
@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3Service;

    public S3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<PresignedURLResponse> generateURL(@RequestBody PresignedURLRequest presignedURLRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(s3Service.generate(presignedURLRequest));
    }

}
