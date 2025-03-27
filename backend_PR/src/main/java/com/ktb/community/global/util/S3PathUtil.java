package com.ktb.community.global.util;

public class S3PathUtil {
    private S3PathUtil() {
        // IMP : Should not be instantiated
    }

    /**
     * IMP : S3 Key 생성
     * 
     * @param entity   객체 ( ex. user, post )
     * @param id       객체 식별자
     * @param fileName 파일의 이름 ( 확장fea자를 포함합니다. )
     * @return
     */
    public static String generatePath(String entity, String id, String fileName) {
        return "Community/" + entity + "/" + id + "/" + fileName;
    }
}
