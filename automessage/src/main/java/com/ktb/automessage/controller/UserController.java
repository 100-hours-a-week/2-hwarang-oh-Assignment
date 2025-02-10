package com.ktb.automessage.controller;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.service.UserService;
import com.ktb.automessage.utils.ConsoleIOUtil;

public class UserController {
    private ConsoleIOUtil consoleIOUtil;
    private UserService userService;

    public UserController(ConsoleIOUtil consoleIOUtil) {
        this.consoleIOUtil = consoleIOUtil;
        this.userService = new UserService(this.consoleIOUtil);
    }

    public boolean loginProcess(KTBUser mainUser) {
        return userService.loginProcess(mainUser);
    }
}
