package com.ktb.automessage.controller;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.service.MessageService;
import com.ktb.automessage.utils.ConsoleIOUtil;

public class MessageController {
    private ConsoleIOUtil consoleIOUtil;
    private MessageService messageService;

    public MessageController(ConsoleIOUtil consoleIOUtil) {
        this.consoleIOUtil = consoleIOUtil;
        this.messageService = new MessageService(this.consoleIOUtil);
    }

    public void sendProcess(KTBUser mainUser, KTBUser targetUser) {
        messageService.sendProcess(mainUser, targetUser);
    }
}
