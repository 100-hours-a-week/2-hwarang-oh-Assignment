package com.ktb.automessage.validation.validator;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.utils.ConsoleIOUtil;
import com.ktb.automessage.validation.Validation;

public class UserValidator {
    private ConsoleIOUtil consoleIOUtil;
    private String userInput;

    public UserValidator(ConsoleIOUtil consoleIOUtil) {
        this.consoleIOUtil = consoleIOUtil;
    }

    public Validation checkKoreanName() {
        Validation validation = new Validation();
        int failCount = 0;
        while (true) {
            if (failCount >= 3) {
                userInput = consoleIOUtil.defaultPrintWithInput("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
                if (userInput.equalsIgnoreCase("Y")) {
                    validation.setValid(false);
                    break;
                } else {
                    failCount = 0;
                    continue;
                }
            }
            userInput = consoleIOUtil.defaultPrintWithInput("📝 사용자님의 한글 이름을 입력해주세요. (예시: 홍길동): ",
                    new MemberNameException());
            try {
                if (validateKoreanName(userInput)) {
                    System.out.println("✅ 이름이 정상적으로 입력되었습니다. " + this.userInput);
                    validation.setTarget(this.userInput);
                    validation.setValid(true);
                    break;
                } else
                    throw new MemberNameException("한글 이름을 입력해 주세요! (예시: 홍길동)");
            } catch (MemberNameException e) {
                System.out.println("⚠ " + e.getMessage());
                failCount++;
            }
        }
        return validation;
    }

    public Validation checkEnglishName(String kName) {
        Validation validation = new Validation();
        int failCount = 0;
        while (true) {
            if (failCount >= 3) {
                userInput = consoleIOUtil.defaultPrintWithInput("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
                if (userInput.equalsIgnoreCase("Y")) {
                    validation.setValid(false);
                    break;
                } else {
                    failCount = 0;
                    continue;
                }
            }
            userInput = consoleIOUtil.defaultPrintWithInput("📝 사용자님의 영어 이름을 입력해주세요. (예시: GilDong.Hong): ",
                    new MemberNameException());
            try {
                if (validateEnglishName(this.userInput)) {
                    System.out.println("✅ 이름이 정상적으로 입력되었습니다. " + this.userInput);
                    validation.setTarget(this.userInput);
                    validation.setValid(true);
                    break;
                } else
                    throw new MemberNameException("영어 이름을 입력해 주세요! (예시: GilDong.Hong)");
            } catch (MemberNameException e) {
                System.out.println("⚠ " + e.getMessage());
                failCount++;
            }
        }
        return validation;
    }

    public Validation checkTrack(String kName) {
        Validation validation = new Validation();
        userInput = consoleIOUtil.defaultPrintWithInput("📝 사용자님의 Track 정보를 입력해주세요. (예시: 풀스택, 인공지능, 클라우드) : ");
        System.out.println("✅ Track 정보가 정상적으로 입력되었습니다. " + this.userInput);
        validation.setTarget(this.userInput);
        validation.setValid(true);
        return validation;
    }

    private boolean validateKoreanName(String name) {
        return name.matches("^[가-힣]+$");
    }

    private boolean validateEnglishName(String name) {
        return name.matches("^[a-zA-Z.]+$");
    }
}
