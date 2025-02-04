package com.ktb.automessage.utils;

import com.ktb.automessage.exception.MemberNameException;

/**
 * MemberUtil Class는 Member의 정보를 처리하는 Util Class입니다.
 */
public class MemberUtil {
    public static String[] parseName(String rawUserData) {
        String eName = rawUserData.split("\\(")[0].trim();
        String kName = rawUserData.split("\\(")[1].split("\\)")[0].trim();
        String track = rawUserData.split("/")[1].trim();
        return new String[] { eName, kName, track };
    }

    public static boolean validateKoreanName(String kName) throws MemberNameException {
        if (kName.isEmpty())
            throw new MemberNameException();
        if (!kName.matches("^[가-힣]+$"))
            throw new MemberNameException("한글 이름을 입력해 주세요! (예시: 홍길동)");
        return true;
    }

    public static boolean validateEnglishName(String eName) throws MemberNameException {
        if (eName.isEmpty())
            throw new MemberNameException();
        if (!eName.matches("^[a-zA-Z.]+$") || eName.matches(".*[가-힣].*"))
            throw new MemberNameException("영어 이름을 입력해 주세요! (예시: GilDong.Hong)");
        return true;
    }
}