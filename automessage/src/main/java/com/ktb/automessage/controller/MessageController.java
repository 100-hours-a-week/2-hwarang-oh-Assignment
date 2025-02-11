package com.ktb.automessage.controller;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.service.MessageService;
import com.ktb.automessage.service.UserDataService;
import com.ktb.automessage.utils.ConsoleIOUtil;

public class MessageController {
    private final MessageService messageService;

    public MessageController(ConsoleIOUtil consoleIOUtil, UserDataService userDataService) {
        this.messageService = new MessageService(consoleIOUtil, userDataService);
    }

    public void sendProcess(KTBUser mainUser, KTBUser targetUser) {
        messageService.sendProcess(mainUser, targetUser);
    }
}
