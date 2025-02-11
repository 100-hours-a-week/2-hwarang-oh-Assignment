package com.ktb.automessage.service;

import com.ktb.automessage.domain.user.KTBUser;
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

    private class AuthCheckThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("🔍 인증 정보를 확인하는 중입니다...");
                Thread.sleep(3000);
                System.out.println("✨ 인증 정보 분석이 완료되었습니다.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class UserDataLoadingThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("📂 사용자 데이터를 불러오는 중입니다...");
                Thread.sleep(2000);
                userDataService.loadUserData();
                System.out.println("📂 사용자 데이터 로딩이 완료되었습니다.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void loginLoading() throws InterruptedException {
        AuthCheckThread authThread = new AuthCheckThread();
        UserDataLoadingThread dataThread = new UserDataLoadingThread();
        authThread.start();
        dataThread.start();

        authThread.join();
        dataThread.join();
        System.out.println("\n✅ 필요한 정보를 모두 불러왔습니다!");
        System.out.println("🚀 KTB AutoMessage에 접속되었습니다.\n");
    }
}
