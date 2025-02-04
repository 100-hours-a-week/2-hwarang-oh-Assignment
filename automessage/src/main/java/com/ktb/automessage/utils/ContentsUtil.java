package com.ktb.automessage.utils;

import com.ktb.automessage.exception.MemberNameException;
import com.ktb.automessage.user.KTBUser;
import com.ktb.automessage.user.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ContentsUtil {
    private String userInput;
    private final Scanner scanner;
    private final HashMap<Integer, KTBUser> userData;
    private final KTBUser mainUser;
    private KTBUser targetUser;

    public ContentsUtil() {
        this.scanner = new Scanner(System.in);
        this.userData = new HashMap<>();
        this.mainUser = new KTBUser();
    }

    // KTB AutoMessage의 기본적인 흐름을 담당하는 Method
    public void defaultProcess() {
        while (true) {
            System.out.println();
            System.out.print("입력할 명령어: ");
            this.userInput = this.scanner.nextLine();

            if (userInput.equalsIgnoreCase("EXIT")) break;
            if (userInput.equalsIgnoreCase("SEND")) sendProcess();
        }
    }

    public boolean startProcess() {
        System.out.print("KTB AutoMessage에 로그인 하시겠습니까? (Y/N) ");
        if (this.scanner.nextLine().equalsIgnoreCase("Y"))
            return true;
        else {
            System.out.print("정말로 로그인 하지 않습니까? (Y/Y) ");
            if (this.scanner.nextLine().equalsIgnoreCase("Y"))
                return true;
            System.out.println("KTB AutoMessage에 접속하지 않습니다.");
            this.scanner.close();
            return false;
        }
    }

    public boolean loginProcess() throws InterruptedException {
        Validation validation;
        System.out.println("로그인을 진행합니다. 사용자님의 정보를 알려주세요!");

        validation = checkKName();
        if (!validation.getValid()) return false;
        this.mainUser.setKName(validation.getTarget());


        validation = checkEName(this.mainUser.getKName());
        if (!validation.getValid()) return false;
        this.mainUser.setEName(validation.getTarget());

        validation = checkTrack(this.mainUser.getKName());
        if (!validation.getValid()) return false;
        this.mainUser.setTrack(validation.getTarget());

        loginLoading();

        return true;
    }

    public void welcomeMessage() throws InterruptedException {
        System.out.println();
        printWithDelay("안녕하세요! KTB AutoMessage에 오신 것을 환영합니다. \n", 25);
        printWithDelay("KTB AutoMessage는 KTB 동료들에게 메시지를 보내는 프로그램입니다. \n", 25);
    }

    public void InfoMessage() throws InterruptedException {
        System.out.println();
        printWithDelay("메시지 종류 \n", 25);
        printWithDelay("1. 기본 메시지 : 조금 어색하다면 안부 인사를 보내보는 것은 어떨까요? \n", 25);
        printWithDelay("2. 감정 메시지 : " + this.mainUser.getKName() + " 님의 진심을 담은 메시지를 보낼 수 있습니다 \n", 25);
        printWithDelay("3. 커스텀 메시지 : " + this.mainUser.getKName() + " 님이 하고싶은 말을 듬뿍 담아서 메시지를 전달할 수 있어요:) \n", 25);
    }

    public void helpMessage() {
        System.out.println();
        System.out.println("Send : 메시지를 보내는 명령어입니다.");
        System.out.println("Info : 메시지 종류를 보여주는 명령어입니다.");
        System.out.println("Help : 도움말을 보여주는 명령어입니다.");
        System.out.println("Exit : 프로그램을 종료하는 명령어입니다.");
        System.out.println("Discord : 재미있는 것이 나올수도..?");
    }

    /**
     * 메시지를 보내는 프로세스를 시작하는 Method
     */
    public void sendProcess() {
        System.out.println();
        System.out.println("메시지를 보내는 프로세스를 시작합니다.");
        System.out.print("메시지를 보낼 사용자 목록을 보시겠습니까? (Y/N): ");

        if (this.scanner.nextLine().equalsIgnoreCase("Y")) {
            sendSelectedTarget();
        }
        else {
            sendInputTarget();
        }
    }

    private boolean sendSelectedTarget() {
        boolean isSelected;
        showUserData();
        System.out.println("메시지를 보내고 싶은 상대방의 번호를 알려주세요!");

        while (true) {
            System.out.print("메시지를 보내고 싶은 상대방의 번호를 알려주세요. (예시: 1): ");
            int targetIdx = Integer.parseInt(this.scanner.nextLine());
            if (this.userData.containsKey(targetIdx)) {
                isSelected = true;
                this.targetUser = this.userData.get(targetIdx);
                break;
            }
            else {
                System.out.println("해당 번호의 사용자가 존재하지 않습니다.");
                System.out.print("보내고 싶은 사람이 없으시면, 직접 입력하시겠습니까? (Y/N): ");
                if (this.scanner.nextLine().equalsIgnoreCase("Y")) {
                    isSelected = sendInputTarget();
                    break;
                }
            }
        }
        return isSelected;
    }

    private boolean sendInputTarget() {
        Validation validation;
        System.out.println("메시지를 보내고 싶은 상대방의 정보를 알려주세요.");

        validation = checkKName();
        if (!validation.getValid()) return false;
        this.targetUser.setKName(validation.getTarget());

        validation = checkEName(this.mainUser.getKName());
        if (!validation.getValid()) return false;
        this.targetUser.setEName(validation.getTarget());

        validation = checkTrack(this.mainUser.getKName());
        if (!validation.getValid()) return false;
        this.targetUser.setTrack(validation.getTarget());

        return true;
    }



    // Private Method
    private void printWithDelay(String message, int delay) throws InterruptedException {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(delay);
        }
    }

    /**
     * TODO : Thread를 활용한 구현으로 추후 수정 예정
     * @throws InterruptedException
     */
    private void loginLoading() throws InterruptedException {
        System.out.println();
        printWithDelay("KTB AutoMessage에 접속에 필요한 인증 정보를 확인합니다...\n", 50);
        getUserData();
        printWithDelay("사용자 목록을 불러오는 중입니다...\n", 50);
        printWithDelay("필요한 정보를 모두 불러왔습니다!\n", 50);
        System.out.println("KTB AutoMessage에 접속되었습니다.\n");
    }

    private void getUserData() {
        ArrayList<KTBUser> userData = UserDataUtil.loadUserData();
        for (int i = 0; i < userData.size(); i++) {
            this.userData.put(i + 1, userData.get(i));
        }
    }

    private void showUserData() {
        System.out.println("사용자 목록을 불러오는 중입니다.");
        int size = this.userData.size();
        int columnCount = 4; // 4열로 출력
        int rowCount = (size + columnCount - 1) / columnCount; // 행 개수 계산

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < size) {
                    row.append(String.format("%-25s", (index + 1) + ". " + this.userData.get(index + 1)));
                }
            }
            System.out.println(row.toString());
        }
        System.out.println("사용자 목록을 불러왔습니다.");
    }

    private Validation checkKName() {
        Validation validation = new Validation();
        int failCount = 0;
        while (true) {
            if (failCount >= 3) {
                System.out.print("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
                if (this.scanner.nextLine().equalsIgnoreCase("Y")) {
                    validation.setValid(false);
                    break;
                }
                else {
                    failCount = 0;
                    continue;
                }
            }
            System.out.print("사용자님의 한글 이름을 입력해주세요. (예시: 홍길동): ");
            this.userInput = this.scanner.nextLine();
            try {
                if (MemberUtil.validateKoreanName(this.userInput.trim())) {
                    System.out.println("이름이 정상적으로 입력되었습니다. " + this.userInput);
                    validation.setTarget(this.userInput);
                    validation.setValid(true);
                    break;
                }
            } catch (MemberNameException e) {
                System.out.println(e.getMessage());
                failCount++;
            }
        }
        return validation;
    }

    private Validation checkEName(String kName) {
        Validation validation = new Validation();
        int failCount = 0;
        while (true) {
            if (failCount >= 3) {
                System.out.print("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
                if (this.scanner.nextLine().equalsIgnoreCase("Y")) {
                    validation.setValid(false);
                    break;
                }
                else {
                    failCount = 0;
                    continue;
                }
            }
            System.out.print(kName + "님의 영어 이름을 입력해주세요. (예시: GilDong.Hong): ");
            this.userInput = this.scanner.nextLine();
            try {
                if (MemberUtil.validateEnglishName(this.userInput.trim())) {
                    System.out.println("이름이 정상적으로 입력되었습니다. " + this.userInput);
                    validation.setTarget(this.userInput);
                    validation.setValid(true);
                    break;
                }
            } catch (MemberNameException e) {
                System.out.println(e.getMessage());
                failCount++;
            }
        }
        return validation;
    }

    private Validation checkTrack(String kName) {
        Validation validation = new Validation();
        int failCount = 0;
        while (true) {
            if (failCount >= 3) {
                System.out.print("❓ KTB AutoMessage를 이용하기 싫으신가요? (Y/N): ");
                if (this.scanner.nextLine().equalsIgnoreCase("Y")) {
                    validation.setValid(false);
                    break;
                }
                else {
                    failCount = 0;
                    continue;
                }
            }
            System.out.print(kName + "님의 Track 정보를 입력해주세요. (예시: 풀스택, 인공지능, 클라우드) : ");
            this.userInput = this.scanner.nextLine();
            if (!this.userInput.isEmpty()) {
                System.out.println("Track 정보가 정상적으로 입력되었습니다. " + this.userInput);
                validation.setTarget(this.userInput);
                validation.setValid(true);
                break;
            }
            else {
                System.out.println("Track 정보를 입력해주세요.");
                failCount++;
            }
        }
        return validation;
    }



}
