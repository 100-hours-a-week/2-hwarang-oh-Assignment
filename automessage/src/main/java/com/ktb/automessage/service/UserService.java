package com.ktb.automessage.service;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.domain.user.User;
import com.ktb.automessage.utils.ConsoleIOUtil;
import com.ktb.automessage.validation.Validation;
import com.ktb.automessage.validation.validator.UserValidator;

public class UserService {
    private final ConsoleIOUtil consoleIOUtil;
    private final UserValidator userValidator;
    private final UserDataService userDataService;

    public UserService(ConsoleIOUtil consoleIOUtil, UserDataService userDataService) {
        this.consoleIOUtil = consoleIOUtil;
        this.userDataService = userDataService;
        this.userValidator = new UserValidator(this.consoleIOUtil);
    }

    public boolean loginProcess(KTBUser mainUser) throws InterruptedException {
        Validation validation;
        consoleIOUtil.defaultPrint("로그인을 진행합니다. 사용자님의 정보를 알려주세요!");

        validation = userValidator.checkKoreanName();
        if (!validation.getValid())
            return false;
        mainUser.setKName(validation.getTarget());

        validation = userValidator.checkEnglishName(mainUser.getKName());
        if (!validation.getValid())
            return false;
        mainUser.setEName(validation.getTarget());

        validation = userValidator.checkTrack(mainUser.getKName());
        if (!validation.getValid())
            return false;
        mainUser.setTrack(validation.getTarget());

        loginLoading();

        return true;
    }

    private void loginLoading() throws InterruptedException {
        userDataService.loadUserData();
        userDataService.displayUserData();
        consoleIOUtil.delayPrint("""
                🔍 KTB AutoMessage에 접속에 필요한 인증 정보를 확인합니다...
                📂 사용자 목록을 불러오는 중입니다...
                ✅ 필요한 정보를 모두 불러왔습니다!
                🚀 KTB AutoMessage에 접속되었습니다.
                """);
    }
}
