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
}