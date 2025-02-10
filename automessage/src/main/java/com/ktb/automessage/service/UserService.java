package com.ktb.automessage.service;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.utils.ConsoleIOUtil;
import com.ktb.automessage.validation.Validation;
import com.ktb.automessage.validation.validator.UserValidator;

public class UserService {
    ConsoleIOUtil consoleIOUtil;
    UserValidator userValidator;

    public UserService(ConsoleIOUtil consoleIOUtil) {
        this.consoleIOUtil = consoleIOUtil;
        this.userValidator = new UserValidator(this.consoleIOUtil);
    }

    public boolean loginProcess(KTBUser mainUser) {
        Validation validation;
        System.out.println("로그인을 진행합니다. 사용자님의 정보를 알려주세요!");

        validation = userValidator.checkKoreanName();
        if (!validation.getValid())
            return false;
        mainUser.setKName(validation.getTarget());

        validation = userValidator.checkEnglishName(null);
        if (!validation.getValid())
            return false;
        mainUser.setEName(validation.getTarget());

        validation = userValidator.checkTrack(null);
        if (!validation.getValid())
            return false;
        mainUser.setTrack(validation.getTarget());

        return true;
    }
}
