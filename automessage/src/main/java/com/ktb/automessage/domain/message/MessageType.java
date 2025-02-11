package com.ktb.automessage.domain.message;

/**
 * MessageType Enum
 * DEFAULT, THANKS, PRAISE, CHEER
 */
public enum MessageType {
    DEFAULT(0, "기본"),
    THANKS(1, "감사"),
    PRAISE(2, "칭찬"),
    CHEER(3, "응원");

    private final int code;
    private final String keyword;

    MessageType(int code, String keyword) {
        this.code = code;
        this.keyword = keyword;
    }

    public int getCode() {
        return code;
    }

    public String getKeyword() {
        return keyword;
    }

    // keyword -> MessageType 변환
    public static MessageType fromKeyword(String keyword) {
        for (MessageType type : values()) {
            if (type.getKeyword().equals(keyword)) {
                return type;
            }
        }
        return DEFAULT;
    }

    // MessageType을 적절한 String으로 변환
    public static String getAvailableKeywords() {
        return String.format("%s 🙏, %s 👏, %s 💪",
                THANKS.getKeyword(),
                PRAISE.getKeyword(),
                CHEER.getKeyword());
    }
}