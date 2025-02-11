package com.ktb.automessage.controller;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.service.UserDataService;
import com.ktb.automessage.service.UserService;
import com.ktb.automessage.utils.ConsoleIOUtil;

public class UserController {
    private final UserService userService;

    public UserController(ConsoleIOUtil consoleIOUtil, UserDataService userDataService) {
        this.userService = new UserService(consoleIOUtil, userDataService);
    }

    public boolean loginProcess(KTBUser mainUser) throws InterruptedException {
        return userService.loginProcess(mainUser);
    }
}
