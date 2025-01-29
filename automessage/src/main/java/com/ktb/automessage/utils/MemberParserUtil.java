package com.ktb.automessage.utils;

public class MemberParserUtil {
    public static String[] parseName(String rawUserData) {
        String eName = rawUserData.split("\\(")[0].trim();
        String kName = rawUserData.split("\\(")[1].split("\\)")[0].trim();
        String track = rawUserData.split("/")[1].trim();
        return new String[] { eName, kName, track };
    }
}