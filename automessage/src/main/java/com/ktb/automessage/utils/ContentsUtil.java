package com.ktb.automessage.utils;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.user.KTBUser;

import java.util.Scanner;

public class ContentsUtil {
    private String userInput;
    private final Scanner scanner;
    private KTBUser MainUser;

    public ContentsUtil() {
        this.scanner = new Scanner(System.in);
    }

    // printWithDelay -> Message 출력 꾸미기
    private void printWithDelay (String message, int delay) throws InterruptedException {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(delay);
        }
    }

    // KTB AutoMessage의 기본적인 흐름을 담당하는 Method
    public void defaultProcess() {
        System.out.print("입력할 명령어 : ");
        this.userInput = this.scanner.nextLine();
    }


    public boolean startProcess() {
        System.out.print("KTB AutoMessage에 로그인 하시겠습니까? (Y/N) ");
        if (this.scanner.nextLine().equalsIgnoreCase("Y")) return true;
        else {
            System.out.print("정말로 로그인 하지 않습니까? (Y/Y) ");
            if (this.scanner.nextLine().equalsIgnoreCase("Y")) return true;
            System.out.println("KTB AutoMessage에 접속하지 않습니다.");
            this.scanner.close();
            return false;
        }
    }

    private boolean checkKName(int failCount) throws MemberNameException {
        if (failCount >= 3) {
            System.out.print("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
            if (this.scanner.nextLine().equalsIgnoreCase("Y")) return false;
            else checkKName(0);
        }

        System.out.print("사용자님의 한글 이름을 입력해주세요. (ex. 홍길동) : ");
        this.userInput = this.scanner.nextLine();
        try {
            MemberUtil.validateKoreanName(this.userInput);
            System.out.println("한글 이름이 정상적으로 입력되었습니다 ");
            return true;
        } catch (MemberNameException e) {
            System.out.println(e.getMessage());
            failCount++;
            return checkKName(failCount);
        }
    }

    public void loginProcess() throws MemberNameException {
        boolean isValid;
        System.out.println("로그인을 진행합니다. 사용자님의 정보를 알려주세요!");
        isValid = checkKName(0);
        if (!isValid) return;
    }

    public void welcomeMessage() throws InterruptedException {
        System.out.println();
        printWithDelay("안녕하세요! KTB AutoMessage에 오신 것을 환영합니다. \n", 25);
        printWithDelay("KTB AutoMessage는 KTB 동료들에게 메시지를 보내는 프로그램입니다. \n", 25);
        System.out.println();
        printWithDelay("Message 종류 \n", 25);
        printWithDelay("1. 기본 메시지 : 조금 어색하다면 안부 인사를 보내보는 것은 어떨까요? \n", 25);
        printWithDelay("2. 감정 메시지 : ___님의 진심을 담은 메시지를 보낼 수 있습니다 \n", 25);
        printWithDelay("3. 커스텀 메시지 : ___님의 하고싶은 말을 듬뿍 담아서 메시지를 전달할 수 있어요:) \n", 25);
    }

    public void helpMessage() {
        System.out.println("Send : 메시지를 보내는 명령어입니다.");
        System.out.println("Help : 도움말을 보여주는 명령어입니다.");
        System.out.println("Exit : 프로그램을 종료하는 명령어입니다.");
        System.out.println("Discord : 재미있는 것이 나올수도..?");
    }

    public void sendProcess() {
        System.out.println("메시지를 보내는 프로세스를 시작합니다.");
    }


}
