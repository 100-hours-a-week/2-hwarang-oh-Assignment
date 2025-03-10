package com.ktb.automessage.service;

import com.ktb.automessage.domain.message.CustomMessage;
import com.ktb.automessage.domain.message.DefaultMessage;
import com.ktb.automessage.domain.message.MessageType;
import com.ktb.automessage.domain.message.TypeMessage;
import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.exception.MessageTypeException;
import com.ktb.automessage.utils.ConsoleIOUtil;
import com.ktb.automessage.validation.Validation;
import com.ktb.automessage.validation.validator.UserValidator;

public class MessageService {
    private String userInput;
    private DefaultMessage message;
    private MessageType messageType;
    private final ConsoleIOUtil consoleIOUtil;
    private final UserValidator userValidator;
    private final UserDataService userDataService;
    private final ReplyService replyService;

    public MessageService(ConsoleIOUtil consoleIOUtil, UserDataService userDataService) {
        this.consoleIOUtil = consoleIOUtil;
        this.userDataService = userDataService;
        this.userValidator = new UserValidator(this.consoleIOUtil);
        this.replyService = new ReplyService();
        this.replyService.startReplyThread();
    }

    public void sendProcess(KTBUser mainUser, KTBUser targetUser) {
        if (!getTargetUser(targetUser)) {
            consoleIOUtil.defaultPrint("🔙 초기 화면으로 돌아갑니다.");
            return;
        }
        if (!sendDefaultMessage(mainUser, targetUser)) {
            this.replyService.addMessageToQueue(mainUser, targetUser, message.getMessage());
            consoleIOUtil.defaultPrint("📨 메시지를 " + targetUser + "님에게 보내습니다.");
            return;
        }
        if (!sendTypeMessage(mainUser, targetUser)) {
            this.replyService.addMessageToQueue(mainUser, targetUser, message.getMessage());
            consoleIOUtil.defaultPrint("📨 메시지를 " + targetUser + "님에게 보내습니다.");
            return;
        }
        sendCustomMessage(mainUser, targetUser);
        this.replyService.addMessageToQueue(mainUser, targetUser, message.getMessage());
        consoleIOUtil.defaultPrint("📨 메시지를 " + targetUser + "님에게 보내습니다.");
    }

    public void stopReplyThread() {
        this.replyService.stopReplyThread();
    }

    private boolean sendDefaultMessage(KTBUser mainUser, KTBUser targetUser) {
        this.message = new DefaultMessage(mainUser, targetUser);
        this.userInput = consoleIOUtil.defaultPrintWithInput("""
                ✉ %s님이 %s님에게 보낼 메시지를 만들었어요

                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                %s
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

                💭 어떠신가요? 더 깊은 의미의 메시지를 보내고 싶으신가요? (Y/N):""".formatted(mainUser, targetUser,
                this.message.getMessage()));
        return this.userInput.equalsIgnoreCase("Y");
    }

    private boolean sendTypeMessage(KTBUser mainUser, KTBUser targetUser) {
        while (true) {
            this.userInput = consoleIOUtil
                    .defaultPrintWithInput("💬 어떤 감정을 보내고 싶으신가요? (" + MessageType.getAvailableKeywords() + "): ",
                            new MessageTypeException());
            try {
                this.messageType = MessageType.fromKeyword(userInput);
                if (this.messageType == MessageType.DEFAULT) {
                    throw new MessageTypeException("⚠ " + userInput + "은(는) 올바른 메시지 타입이 아닙니다!");
                } else
                    break;
            } catch (MessageTypeException e) {
                consoleIOUtil.defaultPrint("""
                        %s
                        💡 사용 가능한 메시지 타입: %s""".formatted(e.getMessage(), MessageType.getAvailableKeywords()));
            }
        }
        this.message = new TypeMessage(mainUser, targetUser, this.messageType);
        this.userInput = consoleIOUtil.defaultPrintWithInput("""
                ✉ %s님이 %s님에게 보낼 메시지를 만들었어요

                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                %s
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

                💭 어떠신가요? 더 깊은 의미의 메시지를 보내고 싶으신가요? (Y/N):""".formatted(mainUser, targetUser,
                this.message.getMessage()));
        return this.userInput.equalsIgnoreCase("Y");
    }

    private void sendCustomMessage(KTBUser mainUser, KTBUser targetUser) {
        this.userInput = consoleIOUtil.defaultPrintWithInput("""
                💌 %s님에게 보낼 진심이 담긴 메시지를 입력해주세요!
                📝 메시지: """.formatted(targetUser));
        this.message = new CustomMessage(mainUser, targetUser, this.messageType, this.userInput);
        consoleIOUtil.defaultPrint("""
                ✉ %s님이 %s님에게 보낼 메시지를 만들었어요

                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                %s
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━""".formatted(mainUser, targetUser, this.message.getMessage()));
    }

    private boolean getTargetUser(KTBUser targetUser) {
        boolean isTargeted = false;
        this.userInput = consoleIOUtil.defaultPrintWithInput("""
                📤 메시지를 보내는 프로세스를 시작합니다.
                📌 메시지를 보낼 사용자 목록을 보시겠습니까? (Y/N):""");
        if (this.userInput.equalsIgnoreCase("Y"))
            isTargeted = sendSelectedTarget(targetUser);
        else
            isTargeted = sendInputTarget(targetUser);
        return isTargeted;
    }

    private boolean sendSelectedTarget(KTBUser targetUser) {
        userDataService.displayUserData();
        KTBUser tempNextUser;
        while (true) {
            this.userInput = consoleIOUtil.defaultPrintWithInput("📌 메시지를 보내고 싶은 상대방의 번호를 알려주세요. (예시: 1): ");
            try {
                int targetIdx = Integer.parseInt(this.userInput);
                if (userDataService.checkUserInData(targetIdx)) {
                    tempNextUser = userDataService.getUserData(targetIdx);
                    targetUser.setKName(tempNextUser.getKName());
                    targetUser.setEName(tempNextUser.getEName());
                    targetUser.setTrack(tempNextUser.getTrack());
                    return true;
                } else {
                    this.userInput = consoleIOUtil.defaultPrintWithInput("""
                            🚫 해당 번호의 사용자가 존재하지 않습니다.
                            📩 보내고 싶은 사람이 없으시면, 직접 입력하시겠습니까? (Y/N):""");
                    if (this.userInput.equalsIgnoreCase("Y"))
                        return sendInputTarget(targetUser);
                }
            } catch (NumberFormatException e) {
                consoleIOUtil.defaultPrint("🚫 숫자만 입력해주세요! (예시: 1)");
            }
        }
    }

    private boolean sendInputTarget(KTBUser targetUser) {
        Validation validation;
        consoleIOUtil.defaultPrint("📌 메시지를 보내고 싶은 상대방의 정보를 입력해주세요.");

        validation = userValidator.checkKoreanName();
        if (!validation.getValid())
            return false;
        targetUser.setKName(validation.getTarget());

        validation = userValidator.checkEnglishName(targetUser.getKName());
        if (!validation.getValid())
            return false;
        targetUser.setEName(validation.getTarget());

        validation = userValidator.checkTrack(targetUser.getKName());
        if (!validation.getValid())
            return false;
        targetUser.setTrack(validation.getTarget());

        System.out.println(targetUser);

        if (!userDataService.checkUserInTrackingData(targetUser.getFullName())) {
            this.userInput = consoleIOUtil.defaultPrintWithInput("""
                    🔍 %s님은 KTB Track 정보에 등록되지 않은 사용자네요?
                    📌 KTB AutoMessage DB에 %s님을 등록하시겠습니까? (Y/N): """.formatted(targetUser, targetUser));
            if (this.userInput.equalsIgnoreCase("Y"))
                userDataService.saveUserData(targetUser);
            else
                consoleIOUtil.defaultPrint("❌ KTB Track 정보에 " + targetUser + "님을 등록하지 않았습니다.");
        }
        return true;
    }
}
