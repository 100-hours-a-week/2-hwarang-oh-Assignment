package com.ktb.community.external.aws.model.dto;

import lombok.Getter;

@Getter
public class PresignedURLRequest {
    String entity;
    String id;
    String fileName;
}
