package com.ktb.automessage.controller;

import java.util.Scanner;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.exception.DefaultEmptyException;
import com.ktb.automessage.utils.ConsoleIOUtil;

public class ContentsController {
    private ConsoleIOUtil consoleIOUtil;
    private UserController userController;
    private String userInput;
    private KTBUser mainUser;
    private KTBUser targetUser;

    public ContentsController() {
        this.consoleIOUtil = new ConsoleIOUtil(new Scanner(System.in));
        this.userController = new UserController(consoleIOUtil);
        this.mainUser = new KTBUser();
        this.targetUser = new KTBUser();
    }

    public void start() throws Exception {
        welcomeMessage();
        if (startProcess()) {
            userController.loginProcess(this.mainUser);
            InfoMessage();
            helpMessage();
        }
    }

    private boolean startProcess() throws DefaultEmptyException {
        this.userInput = consoleIOUtil.defaultPrintWithInput("🔑 KTB AutoMessage에 로그인 하시겠습니까? (Y/N) ");
        if (this.userInput.equalsIgnoreCase("Y"))
            return true;
        else {
            this.userInput = consoleIOUtil.defaultPrintWithInput("❓ 정말로 로그인 하지 않습니까? (Y/N) ");
            if (this.userInput.equalsIgnoreCase("Y")) {
                System.out.println("🚪 KTB AutoMessage에 접속하지 않습니다.");
                consoleIOUtil.closeScanner();
                return false;
            }
            return true;
        }
    }

    private void welcomeMessage() throws InterruptedException {
        consoleIOUtil.delayPrint("""
                👋 안녕하세요! KTB AutoMessage에 오신 것을 환영합니다.
                📩 KTB AutoMessage는 KTB 동료들에게 메시지를 보내는 프로그램입니다.
                """);
    }

    private void InfoMessage() throws InterruptedException {
        consoleIOUtil.delayPrint("""
                📌 메시지 종류
                1️⃣ 기본 메시지 : 조금 어색하다면 안부 인사를 보내보는 것은 어떨까요? 😊
                2️⃣ 감정 메시지 : 당신의 진심을 담은 메시지를 보낼 수 있습니다 💖
                3️⃣ 커스텀 메시지 : 당신이 하고싶은 말을 듬뿍 담아서 메시지를 전달할 수 있어요 😊✨
                """);
    }

    private void helpMessage() {
        consoleIOUtil.defaultPrint("""
                📢 도움말 안내
                ✉ Send : 메시지를 보내는 명령어입니다.
                ℹ Info : 메시지 종류를 보여주는 명령어입니다.
                ❓ Help : 도움말을 보여주는 명령어입니다.
                ❌ Exit : 프로그램을 종료하는 명령어입니다.
                🎵 Discord : 재미있는 것이 나올수도..?
                """);
    }

}
