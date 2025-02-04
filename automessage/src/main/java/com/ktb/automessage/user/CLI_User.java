package com.ktb.automessage.user;

import com.ktb.automessage.utils.MemberUtil;

public class CLI_User {
    private final String member;

    public CLI_User(String member) {
        this.member = member;
    }

    public KTBUser toKTBUser() {
        String[] userData = MemberUtil.parseName(this.member);
        return new KTBUser(userData[1], userData[0], userData[2]);
    }
}
